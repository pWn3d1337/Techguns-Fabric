package techguns.client.modelloader;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.resource.metadata.AnimationResourceMetadata;
import net.minecraft.client.texture.*;
import net.minecraft.client.util.SpriteIdentifier;
//import net.minecraft.client.util.math.Vector3d;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import techguns.client.models.obj.TGObjBakedModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class TGObjModel implements UnbakedModel {

    protected static final SpriteIdentifier BLOCK_ATLAS_SPRITE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, null);

    protected ArrayList<Vector3f> vertices;
    protected ArrayList<Vector3f> normals;
    protected ArrayList<Vec2f> uvs;
    protected HashMap<String, TGObjMtl> mtls;
    protected HashMap<String, SubOjbect> object_material_map;
    protected boolean custom_texture;
    protected boolean flip_v;

    public TGObjModel(TGObjLoader.ModelLoadParameters params) {
        this.vertices = new ArrayList<>();
        this.normals = new ArrayList<>();
        this.uvs = new ArrayList<>();
        this.mtls = new HashMap<>();
        this.object_material_map = new HashMap<>();
        this.flip_v = params.flip_v;
        this.custom_texture = params.custom_texture;
    }

    protected static class SubOjbect {

        public ArrayList<Face> faces;

        public SubOjbect() {

            this.faces = new ArrayList<Face>();
        }

        public void addFace(Face f){
            faces.add(f);
        }
    }

    protected static class FaceIndexEntry {
        private int[] values = new int[3];

        public FaceIndexEntry(int i0, int i1, int i2){
            this.values[0]=i0;
            this.values[1]=i1;
            this.values[2]=i2;
        }

        public int get(int index) {
            switch (index) {
                case 0:
                case 1:
                case 2:
                    return values[index];
                default:
                    return -1;
            }
        }
    }


    protected static class Face {
        public FaceIndexEntry vertices;
        public FaceIndexEntry normals;
        public FaceIndexEntry textures;

        public Face(String[] splitted_line){
            int[] v1 = to_int(splitted_line[1].split("/"));
            int[] v2 = to_int(splitted_line[2].split("/"));
            int[] v3 = to_int(splitted_line[3].split("/"));

            this.vertices = new FaceIndexEntry(v1[0], v2[0], v3[0]);
            this.normals = new FaceIndexEntry(v1[2], v2[2], v3[2]);
            this.textures = new FaceIndexEntry(v1[1], v2[1], v3[1]);
        }
        //also subtract -1 because .obj index start with 1 instead of 0
        protected static int[] to_int(String[] str_arr){
            int[] int_arr = new int[str_arr.length];
            for(int i=0; i<str_arr.length; i++){
                int_arr[i] = Integer.parseInt(str_arr[i])-1;
            }
            return int_arr;
        }
    }

    public static TGObjModel parseFromFile(BufferedReader br, Identifier file, ResourceManager rm, TGObjLoader.ModelLoadParameters params) throws IOException {
        TGObjModel model = new TGObjModel(params);
        int linecounter=0;

        SubOjbect currentObject = new SubOjbect();

        try {
            while (br.ready()) {
                String line = br.readLine();
                if (line != null && !line.isEmpty()) {
                    String trimmed_line = line.trim();
                    if (!trimmed_line.startsWith("#")) {
                        String[] tokens = trimmed_line.split("\\s+");

                        switch (tokens[0]) {
                            case "mtllib":
                                model.mtls = TGObjMtl.loadMtl(file, tokens[1], rm);
                                break;
                            case "v":
                                float x = Float.parseFloat(tokens[1]);
                                float y = Float.parseFloat(tokens[2]);
                                float z = Float.parseFloat(tokens[3]);
                                model.vertices.add(new Vector3f(x,y,z));
                                break;
                            case "vt":
                                float u = Float.parseFloat(tokens[1]);
                                float v = Float.parseFloat(tokens[2]);
                                if(model.flip_v){
                                    v = 1.0f-v;
                                }
                                model.uvs.add(new Vec2f(u,v));
                                break;
                            case "vn":
                                float nx = Float.parseFloat(tokens[1]);
                                float ny = Float.parseFloat(tokens[2]);
                                float nz = Float.parseFloat(tokens[3]);
                                model.normals.add(new Vector3f(nx,ny,nz));
                                break;
                            case "g":
                                //groud -ignored
                                break;
                            case "o":
                                //object -ignored
                                break;
                            case "usemtl":
                                String material = tokens[1];
                                if (model.mtls.containsKey(material)){
                                    currentObject = new SubOjbect();
                                    model.object_material_map.put(material,currentObject);

                                } else {
                                    throw new Exception("Unknown material: "+material);
                                }
                                break;
                            case "s":
                                //smoothing group -ignored
                                break;
                            case "f":
                                currentObject.faces.add(new Face(tokens));
                                break;
                            default:
                                throw new Exception("Unexpected line start: "+ tokens[0]);
                        }
                    }
                }
                linecounter++;
            }
        } catch (Exception e){
            throw new IOException("Error parsing "+file.toString()+" in line "+ (linecounter+1) +": "+e.getClass() + ":" +e.getMessage());
        }

        return model;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptySet();
    }

    @Override
    public void setParents(Function<Identifier, UnbakedModel> modelLoader) {
       //TODO ? needed?
    }

    // @Override
  // public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
  //     List<SpriteIdentifier> sprites = new ArrayList<>();
  //     for (TGObjMtl mtl : this.mtls.values()){
  //         sprites.add(new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, mtl.map_kd));
  //     }
  //     return sprites;
  // }

   public static class DummySprite extends Sprite
   {
       /**
        * Just to get a public constructor
        */
       protected DummySprite(Identifier atlasId, SpriteContents contents, int maxLevel, int atlasWidth, int atlasHeight, int x) {
           super(atlasId, contents, maxLevel, atlasHeight, atlasHeight, x);
       }
   }

    @Nullable
    @Override
    public BakedModel bake(Baker loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId){
        MeshBuilder meshBuilder = RendererAccess.INSTANCE.getRenderer().meshBuilder();
        QuadEmitter quadEmitter = meshBuilder.getEmitter();

        for (String mat : this.object_material_map.keySet()){
            TGObjMtl material = this.mtls.get(mat);
            SubOjbect obj = this.object_material_map.get(mat);

            Sprite sprite=null;
            if (!custom_texture) {
                sprite = textureGetter.apply(new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, material.map_kd));
            }
            else {
                //dummy
                NativeImage img = new NativeImage(16,16,false);
                SpriteContents contents = new SpriteContents(material.map_kd, new SpriteDimensions(16,16), img, AnimationResourceMetadata.EMPTY);
                sprite = new DummySprite(material.map_kd, contents, 0, 16, 16, 0);
            }

            for (int i=0; i< obj.faces.size(); i++){
                Face face = obj.faces.get(i);

                for (int j=0;j<4;j++) {
                    //duplicate last vertex to create quad
                    int k=j;
                    if (k>=3){
                        k=2;
                    }
                    Vector3f v_pos = vertices.get(face.vertices.get(k));
                    quadEmitter.pos(j, v_pos);
                    Vector3f v_normal = normals.get(face.normals.get(k));
                    quadEmitter.normal(j, v_normal);
                    Vec2f uv = uvs.get(face.textures.get(k));
                    quadEmitter.sprite(j, 0, uv.x, uv.y);
                }

                quadEmitter.spriteColor(0, -1,-1,-1, -1);
                quadEmitter.material(RendererAccess.INSTANCE.getRenderer().materialFinder().find());
                quadEmitter.colorIndex(1);
                quadEmitter.spriteBake(0, sprite, MutableQuadView.BAKE_NORMALIZED);
                quadEmitter.emit();
            }

        }

        return new TGObjBakedModel(meshBuilder.build(), ModelTransformation.NONE);
    }
}
