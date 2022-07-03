package techguns.client.modelloader;

import net.minecraft.client.util.math.Vector3d;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Optional;

public class TGObjMtl {
    public String material;
    public Vector3d kd = new Vector3d(1f,1f,1f);
    public Identifier map_kd;

    public static HashMap<String, TGObjMtl> loadMtl(Identifier path, String name, ResourceManager rm) throws IOException {
        HashMap<String, TGObjMtl> mtls = new HashMap<>();
        TGObjMtl mtl = new TGObjMtl();

        String p = path.getPath();
        int pos = p.lastIndexOf('/');
        String dir = p.substring(0,pos);

        Identifier mtlid = new Identifier(path.getNamespace(), dir+"/"+name);
        Optional<Resource> r = rm.getResource(mtlid);
        if (!r.isEmpty()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(r.get().getInputStream()));

            while (br.ready()) {
                String line = br.readLine();
                String[] tokens = line.trim().split(" ");

                switch (tokens[0]) {
                    case "newmtl":
                        if (mtl.material != null) {
                            mtls.put(mtl.material, mtl);
                            mtl = new TGObjMtl();
                        }
                        mtl.material = tokens[1];
                        break;
                    case "Kd":
                        mtl.kd = new Vector3d(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3]));
                        break;
                    case "map_Kd":
                        mtl.map_kd = new Identifier(tokens[1]);
                        break;
                    default:
                        //only 1 texture per MTL supported, no other features needed
                        break;
                }

            }
            if (mtl.material != null) {
                mtls.put(mtl.material, mtl);
            }
        }
        return mtls;
    }

}
