package techguns.recipes;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//import com.sun.org.apache.regexp.internal.RE;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import techguns.TGConfig;
import techguns.TGIdentifier;
import techguns.TGItems;
import techguns.Techguns;
import techguns.items.guns.GenericGun;
import techguns.items.guns.GenericGunMeleeCharge;
import techguns.items.guns.ammo.AmmoType;
import techguns.items.guns.ammo.AmmoTypes;
import techguns.items.guns.ammo.AmmoVariant;


/**
 * Based on:
 * https://gist.github.com/williewillus/a1a899ce5b0f0ba099078d46ae3dae6e
 *
 * added some stuff, modified for 1.16
 */
public class RecipeJsonConverter {
    private static final String dataDirPath = "../../src/main/resources/data";
    private static final String recipeDirPath = dataDirPath + "/techguns/recipes";

    /*private static HashMap<String,String> factories = new HashMap<>();
    static {
        factories.put(Recipewriter.hardenedGlassOrGlass, OreDictIngredientHardenedGlass.class.getName());
        factories.put(Recipewriter.electrumOrGold, OreDictIngredientElectrumOrGold.class.getName());
        factories.put(Recipewriter.itemStackHasNBTInt, IngredientFactoryMatchNBTInt.class.getName());
    }*/

   public static final String AMMO_CHANGE_COPY_NBT_RECIPE = "ammo_change_crafting";
   /* private static HashMap<String,String> recipe_types = new HashMap<>();
    static {
        recipe_types.put(AMMO_CHANGE_COPY_NBT_RECIPE, AmmoSwitchRecipeFactory.class.getName());
        recipe_types.put(MiningToolUpgradeHeadRecipeFactory.MINING_TOOL_UPGRADE_RECIPE, MiningToolUpgradeHeadRecipeFactory.class.getName());
        recipe_types.put(ShapedOreRecipeCopyNBTFactory.COPY_NBT_RECIPE, ShapedOreRecipeCopyNBTFactory.class.getName());
        recipe_types.put(AmmoSumRecipeFactory.AMMO_SUM_RECIPE, AmmoSumRecipeFactory.class.getName());
    }*/

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File RECIPE_DIR = null;
    private static final Set<String> USED_OD_NAMES = new TreeSet<>();

    private static File TAG_DIR_TECHGUNS = null;
    private static File TAG_DIR_COMMON = null;

    private static void remove_json_from_dir(File dir){
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".json");
            }
        });
        System.out.println("Deleting json files, THIS SHOULD ONLY BE DONE IN DEV ENVIRONMENT!!!");
        if (files != null) {
            Arrays.stream(files).forEach(f -> {
                f.delete();
            });
        }
    }

    private static void setupDir() {
        if (RECIPE_DIR == null) {
            RECIPE_DIR = FabricLoader.getInstance().getConfigDir().resolve(recipeDirPath).toFile();
            remove_json_from_dir(RECIPE_DIR);
        }

        if (TAG_DIR_TECHGUNS == null){
            TAG_DIR_TECHGUNS = FabricLoader.getInstance().getConfigDir().resolve(dataDirPath+"/techguns/tags").toFile();
            remove_json_from_dir(TAG_DIR_TECHGUNS);
        }

        if (TAG_DIR_COMMON == null){
            TAG_DIR_COMMON = FabricLoader.getInstance().getConfigDir().resolve(dataDirPath+"/c/tags").toFile();
            remove_json_from_dir(TAG_DIR_COMMON);
        }

        if (!RECIPE_DIR.exists()) {
            RECIPE_DIR.mkdir();
        }
        if (!TAG_DIR_COMMON.exists()) {
            TAG_DIR_COMMON.mkdir();
        }
        if (!TAG_DIR_TECHGUNS.exists()) {
            TAG_DIR_TECHGUNS.mkdir();
        }
    }

    public static void write_special_recipetype(String jsonname, Identifier recipename){
        Map<String, Object> json = new TreeMap<>();
        json.put("type", recipename.toString());

        File f = new File(RECIPE_DIR, jsonname + ".json");
        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write_tag_jsons(){
        for (Map.Entry<Identifier, List<Item>> entry : Recipewriter.TAG_LIST.entrySet()) {
            String dir = entry.getKey().getNamespace();
            String name = entry.getKey().getPath();

            Map<String, Object> json = getTagJson(entry.getValue());

            File f = new File((dir.equals("c") ? TAG_DIR_COMMON : TAG_DIR_TECHGUNS)+"/items", name + ".json");
            try (FileWriter w = new FileWriter(f)) {
                GSON.toJson(json, w);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static Map<String, Object> getTagJson(List<Item> items){
        Map<String, Object> json = new TreeMap<>();
        json.put("replace", false);
        List<String> values;

        values = items.stream().map(item -> Registry.ITEM.getId(item).toString()).collect(Collectors.toList());
        json.put("values", values);
        return json;
    }

    public static void addShapedRecipe(ItemStack result, Object... components) {
        setupDir();

        write_tag_jsons();
        // GameRegistry.addShapedRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        List<String> pattern = new ArrayList<>();
        int i = 0;
        while (i < components.length && components[i] instanceof String) {
            pattern.add((String) components[i]);
            i++;
        }
        json.put("pattern", pattern);

        boolean isOreDict = false;
        Map<String, Map<String, Object>> key = new HashMap<>();
        Character curKey = null;
        for (; i < components.length; i++) {
            Object o = components[i];
            if (o instanceof Character) {
                if (curKey != null)
                    throw new IllegalArgumentException("Provided two char keys in a row");
                curKey = (Character) o;
            } else {
                if (curKey == null)
                    throw new IllegalArgumentException("Providing object without a char key");
                if (o instanceof String)
                    throw new IllegalArgumentException("Providing oredict Strings is no longer supported, use Identifiers for tags");
                key.put(Character.toString(curKey), serializeItem(o));
                curKey = null;
            }
        }
        json.put("key", key);
        json.put("type", "minecraft:crafting_shaped");
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = ""; //no more subtypes in 1.16

        String name = Registry.ITEM.getId(result.getItem()).getPath();
        /*if(result.getItem()  instanceof GenericItemShared) {
            suffix = suffix +"_"+ TGItems.SHARED_ITEM.get(result.getItemDamage()).getName();
        }*/

        if(result.getItem() instanceof BlockItem) {
            BlockItem bi = (BlockItem) result.getItem();

            Block b = bi.getBlock();

            /*if (b instanceof BasicMachine) {
                BasicMachine bm = (BasicMachine) b;
                suffix += "_"+ bm.getNameSuffix(result.getMetadata());
            }*/
        }

        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addEmptyGunRecipe(GenericGun gun, Object... components) {
        addGunShapedRecipe(gun, false, AmmoTypes.TYPE_DEFAULT, "", components);
    }

    public static void addGunShapedRecipe(GenericGun gun, boolean full, String ammoVariant, String camo, Object... components) {
        addMiningToolShapedRecipe(gun, full, ammoVariant, camo, -1, components);
    }

    public static void addMiningToolShapedRecipe(GenericGun gun, boolean full, String ammoVariant, String camo, int mininghead, Object... components) {
        Map<String, Object> json = new HashMap<>();

        List<String> pattern = new ArrayList<>();
        int i = 0;
        while (i < components.length && components[i] instanceof String) {
            pattern.add((String) components[i]);
            i++;
        }
        json.put("pattern", pattern);

        Map<String, Map<String, Object>> key = new HashMap<>();
        Character curKey = null;
        for (; i < components.length; i++) {
            Object o = components[i];
            if (o instanceof Character) {
                if (curKey != null)
                    throw new IllegalArgumentException("Provided two char keys in a row");
                curKey = (Character) o;
            } else {
                if (curKey == null)
                    throw new IllegalArgumentException("Providing object without a char key");
                if (o instanceof String)
                    throw new IllegalArgumentException("Providing oredict Strings is no longer supported, use Identifiers for tags");
                key.put(Character.toString(curKey), serializeItem(o));
                curKey = null;
            }
        }
        json.put("key", key);
        json.put("type", "techguns:crafting_shaped_nbt");
        if (mininghead >= 0){
            json.put("result", serializeGunTool((GenericGunMeleeCharge) gun, full, ammoVariant, mininghead));
        } else {
            json.put("result", serializeGun(gun, full, ammoVariant, camo));
        }

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "";
        if(!ammoVariant.equals(AmmoTypes.TYPE_DEFAULT)){
            suffix+="_"+ammoVariant.toLowerCase(Locale.ROOT);
        }
        if(!full){
            suffix+="_empty";
        }
        if(camo != null && !camo.equals("")){
            suffix+="_"+camo;
        }

        String name = Registry.ITEM.getId(gun).getPath();

        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addAmmoTransferRecipe(GenericGun gun, boolean full, String ammoVariant, String camo, Object... components) {
        Map<String, Object> json = new HashMap<>();

        List<String> pattern = new ArrayList<>();
        int i = 0;
        while (i < components.length && components[i] instanceof String) {
            pattern.add((String) components[i]);
            i++;
        }
        json.put("pattern", pattern);

        Map<String, Map<String, Object>> key = new HashMap<>();
        Character curKey = null;
        for (; i < components.length; i++) {
            Object o = components[i];
            if (o instanceof Character) {
                if (curKey != null)
                    throw new IllegalArgumentException("Provided two char keys in a row");
                curKey = (Character) o;
            } else {
                if (curKey == null)
                    throw new IllegalArgumentException("Providing object without a char key");
                if (o instanceof String)
                    throw new IllegalArgumentException("Providing oredict Strings is no longer supported, use Identifiers for tags");
                key.put(Character.toString(curKey), serializeItem(o));
                curKey = null;
            }
        }
        json.put("key", key);
        json.put("type", "techguns:transfer_ammo");
        json.put("result", serializeGun(gun, full, ammoVariant, camo));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "";
        if(!ammoVariant.equals(AmmoTypes.TYPE_DEFAULT)){
            suffix+="_"+ammoVariant.toLowerCase(Locale.ROOT);
        }
        if(!full){
            suffix+="_empty";
        }
        if(camo != null && !camo.equals("")){
            suffix+="_"+camo;
        }

        String name = Registry.ITEM.getId(gun).getPath();

        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addShapelessRecipe(Item result, Object... components)
    {
        addShapelessRecipe(new ItemStack(result,1), components);
    }

    public static void addShapelessRecipe(ItemStack result, Object... components)
    {
        setupDir();

        // addShapelessRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        boolean isOreDict = false;
        List<Map<String, Object>> ingredients = new ArrayList<>();
        for (Object o : components) {
            if (o instanceof String)
                isOreDict = true;
            ingredients.add(serializeItem(o));
        }
        json.put("ingredients", ingredients);
        json.put("type", isOreDict ? "forge:ore_shapeless" : "minecraft:crafting_shapeless");
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "";

        String name = Registry.ITEM.getId(result.getItem()).getPath();
        /*if(result.getItem()  instanceof GenericItemShared) {
            suffix = suffix +"_"+ TGItems.SHARED_ITEM.get(result.getItemDamage()).getName();
        }*/

        if(result.getItem() instanceof BlockItem) {
            BlockItem bi = (BlockItem) result.getItem();

            Block b = bi.getBlock();

            /*if (b instanceof BasicMachine) {
                BasicMachine bm = (BasicMachine) b;
                suffix += "_"+ bm.getNameSuffix(result.getMetadata());
            }*/
        }


        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMiningheadChangeRecipe(GenericGunMeleeCharge gun, int headlevel, Object... components)
    {
        Map<String, Object> json = new HashMap<>();

        List<Map<String, Object>> ingredients = new ArrayList<>();
        for (Object o : components) {
            ingredients.add(serializeItem(o));
        }
        json.put("ingredients", ingredients);
        json.put("type", "techguns:mininghead_upgrade");
        json.put("result", serializeGunTool(gun, true, AmmoTypes.TYPE_DEFAULT, headlevel));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "";

        String name = Registry.ITEM.getId(gun).getPath();

        File f = new File(RECIPE_DIR, name + suffix + ".json");
        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTGManualRecipe( Object... components) {
        setupDir();

        // addShapelessRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        boolean isOreDict = false;
        List<Map<String, Object>> ingredients = new ArrayList<>();
        for (Object o : components) {
            if (o instanceof String)
                isOreDict = true;
            ingredients.add(serializeItem(o));
        }
        json.put("ingredients", ingredients);
        json.put("type", isOreDict ? "forge:ore_shapeless" : "minecraft:crafting_shapeless");
        CompoundTag tags = new CompoundTag();
        tags.putString("patchouli:book", Techguns.MODID+":techguns_manual");
        json.put("result", serializeItemFromResourceLocation(new Identifier("patchouli", "guide_book"), tags));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "";

        String name = "techguns_manual";//result.getItem().getRegistryName().getResourcePath();

        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addShapelessAmmoSwapRecipe(GenericGun gun, AmmoType type, String key)
    {
        setupDir();

        // addShapelessRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        boolean isOreDict = false;
        List<Map<String, Object>> ingredients = new ArrayList<>();
			/*for (Object o : components) {
				if (o instanceof String)
					isOreDict = true;
				ingredients.add(serializeItem(o));
			}*/
        ingredients.add(serializeItem(new ItemStack(gun,1)));
        ingredients.add(serializeItem(type.getAmmo(type.getIDforVariantKey(key))[0]));
        ItemStack result = new ItemStack(gun,1);

        json.put("ingredients", ingredients);
        json.put("type", "techguns:"+AMMO_CHANGE_COPY_NBT_RECIPE);
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "_ammo_"+key;

        String name = Registry.ITEM.getId(result.getItem()).getPath();

        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 /*   public static void addShapelessMiningHeadUpgradeRecipe(GenericGun gun, ItemStack upgradeItem, String key, int value)
    {
        setupDir();

        // addShapelessRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        boolean isOreDict = false;
        List<Map<String, Object>> ingredients = new ArrayList<>();

        ingredients.add(serializeItemNBTTag(new ItemStack(gun,1),key, value));
        ingredients.add(serializeItem(upgradeItem));
        ItemStack result = new ItemStack(gun,1);

        json.put("ingredients", ingredients);
        json.put("type", "techguns:"+MiningToolUpgradeHeadRecipeFactory.MINING_TOOL_UPGRADE_RECIPE);
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "_upgrade_"+upgradeItem.getItem().getUnlocalizedName();

        String name = result.getItem().getRegistryName().getResourcePath();


        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*public static void addShapedCopyNBTRecipe(ItemStack result, Object... components) {
        addShapedRecipeSpecialType(result, new Identifier(Techguns.MODID, ShapedOreRecipeCopyNBTFactory.COPY_NBT_RECIPE), components);
    }

    public static void addShapedAmmoSumRecipe(ItemStack result, Object... components) {
        addShapedRecipeSpecialType(result, new Identifier(Techguns.MODID, AmmoSumRecipeFactory.AMMO_SUM_RECIPE), components);
    }*/

    public static void addShapedRecipeSpecialType(ItemStack result, Identifier type, Object... components) {
        setupDir();

        // GameRegistry.addShapedRecipe(result, components);

        Map<String, Object> json = new HashMap<>();

        List<String> pattern = new ArrayList<>();
        int i = 0;
        while (i < components.length && components[i] instanceof String) {
            pattern.add((String) components[i]);
            i++;
        }
        json.put("pattern", pattern);

        boolean isOreDict = true;
        Map<String, Map<String, Object>> key = new HashMap<>();
        Character curKey = null;
        for (; i < components.length; i++) {
            Object o = components[i];
            if (o instanceof Character) {
                if (curKey != null)
                    throw new IllegalArgumentException("Provided two char keys in a row");
                curKey = (Character) o;
            } else {
                if (curKey == null)
                    throw new IllegalArgumentException("Providing object without a char key");
                if (o instanceof String)
                    isOreDict = true;
                key.put(Character.toString(curKey), serializeItem(o));
                curKey = null;
            }
        }
        json.put("key", key);
        //json.put("type", isOreDict ? "forge:ore_shaped" : "minecraft:crafting_shaped");
        json.put("type", type.toString());
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "";

        String name = Registry.ITEM.getId(result.getItem()).getPath();
        /*if(result.getItem()  instanceof GenericItemShared) {
            suffix = suffix +"_"+ TGItems.SHARED_ITEM.get(result.getItemDamage()).getName();
        }*/
        if(result.getItem() instanceof BlockItem) {
            BlockItem ib = (BlockItem) result.getItem();

            Block b = ib.getBlock();

            /*if (b instanceof BasicMachine) {
                BasicMachine bm = (BasicMachine) b;
                suffix += "_"+ bm.getNameSuffix(result.getMetadata());
            }*/
        }

        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> serializeItem(Object thing) {
        if (thing instanceof Item) {
            return serializeItem(new ItemStack((Item) thing));
        }
        if (thing instanceof Block) {
            return serializeItem(new ItemStack((Block) thing));
        }
        if (thing instanceof ItemStack) {
            ItemStack stack = (ItemStack) thing;
            Map<String, Object> ret = new HashMap<>();
            ret.put("item", Registry.ITEM.getId(stack.getItem()).toString());
            if (stack.getDamage() != 0) {
                ret.put("data", stack.getDamage());
            }
            if (stack.getCount() > 1) {
                ret.put("count", stack.getCount());
            }

            /*if (stack.hasTag()) {
                throw new IllegalArgumentException("nbt not implemented");
            }*/

            return ret;
        }
        if (thing instanceof Identifier){
            Map<String, Object> ret = new HashMap<>();
            ret.put("tag", thing.toString());
            return ret;
        }
        /*
        if (thing instanceof String) {
            Map<String, Object> ret = new HashMap<>();
            USED_OD_NAMES.add((String) thing);
            ret.put("item", "#" + ((String) thing).toUpperCase(Locale.ROOT));
            return ret;
        }*/

        throw new IllegalArgumentException("Not a block, item, stack, or tag");
    }

    private static Map<String, Object> serializeGun(GenericGun gun, boolean full, String ammo_variant, String camo) {

        ItemStack stack = new ItemStack(gun,1);
        Map<String, Object> ret = new HashMap<>();
        ret.put("item", Registry.ITEM.getId(gun).toString());
        TreeMap<String, Object> data = new TreeMap<>();
        data.put("camo", camo);
        data.put("ammovariant", ammo_variant);
        data.put("ammo", full ? gun.getClipsize() : 0);
        ret.put("data", data);

        return ret;

    }

    private static Map<String, Object> serializeGunTool(GenericGunMeleeCharge gun, boolean full, String ammo_variant, int mininghead){
        Map<String, Object> ret = serializeGun(gun, full, ammo_variant, "");
        Map<String, Object> data = (Map<String, Object>) ret.get("data");
        data.put("mininghead", mininghead);

        return ret;
    }

    private static Map<String, Object> serializeItemFromResourceLocation(Identifier item, CompoundTag tags) {

        Map<String, Object> ret = new HashMap<>();
        ret.put("item", item.toString());

        Map<String,Object> tags_m = new HashMap<>();
        for(String s: tags.getKeys()) {
            String str = tags.getString(s);
            if(str!=null && !str.isEmpty())
                tags_m.put(s, str);
        }

        ret.put("nbt", tags_m);

        return ret;

    }

    private static Map<String, Object> serializeItemNBTTag(ItemStack stack, String key, Object value) {

        Map<String, Object> ret = new HashMap<>();

        ret.put("type", Recipewriter.itemStackHasNBTInt);
        ret.put("item", Registry.ITEM.getId(stack.getItem()).toString());
        if (stack.getDamage() != 0) {
            ret.put("data", stack.getDamage());
        }
        if (stack.getCount() > 1) {
            ret.put("count", stack.getCount());
        }

        ret.put("key", key);
        ret.put("value", value);

        return ret;
    }

    public static void addAmmoBenchRecipe(ItemStack result)
    {
        setupDir();

        Map<String, Object> json = new HashMap<>();

        json.put("type", "techguns:ammobench_crafting");
        json.put("result", serializeItem(result));

        // names the json the same name as the output's registry name
        // repeatedly adds _alt if a file already exists
        // janky I know but it works
        String suffix = "";

        String name = Registry.ITEM.getId(result.getItem()).getPath();

        if(result.getItem() instanceof BlockItem) {
            BlockItem bi = (BlockItem) result.getItem();

            Block b = bi.getBlock();
        }

        File f = new File(RECIPE_DIR, name + suffix + ".json");

        while (f.exists()) {
            suffix += "_alt";
            f = new File(RECIPE_DIR, name + suffix + ".json");
        }

        try (FileWriter w = new FileWriter(f)) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public static void generateConstants() {
        List<Map<String, Object>> json = new ArrayList<>();
        for (String s : USED_OD_NAMES) {
            Map<String, Object> entry = new HashMap<>();

            entry.put("name", s.toUpperCase(Locale.ROOT));
            if(factories.containsKey(s)) {
                entry.put("ingredient", ImmutableMap.of("type", s));
            } else {
                entry.put("ingredient", ImmutableMap.of("type", "forge:ore_dict", "ore", s));
            }
            json.add(entry);
        }

        try (FileWriter w = new FileWriter(new File(RECIPE_DIR, "_constants.json"))) {
            GSON.toJson(json, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /*public static void generateFactories() {

        Map<String, Object> entry = new HashMap<>();
        entry.put("ingredients", factories);
        entry.put("recipes", recipe_types);


        try (FileWriter w = new FileWriter(new File(RECIPE_DIR, "_factories.json"))) {
            GSON.toJson(entry, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
