package org.bro.tubesoop2.utils;

public class Utils {
    public static String toResourceFactoryKeys(String filepath) {
        String key = ""; // Initialize the key as an empty string.
        switch (filepath) {
            // Handle Animal paths
            case "assets/Animal/AYAM.png":
                key = "AYAM";
                break;
            case "assets/Animal/BERUANG.png":
                key = "BERUANG";
                break;
            case "assets/Animal/DOMBA.png":
                key = "DOMBA";
                break;
            case "assets/Animal/HIU_DARAT.png":
                key = "HIU_DARAT";
                break;
            case "assets/Animal/KUDA.png":
                key = "KUDA";
                break;
            case "assets/Animal/SAPI.png":
                key = "SAPI";
                break;

            // Handle Item paths
            case "assets/Item/Accelerate.png":
                key = "ACCELERATE";
                break;
            case "assets/Item/Delay.png":
                key = "DELAY";
                break;
            case "assets/Item/Destroy.png":
                key = "DESTROY";
                break;
            case "assets/Item/INSTANT_HARVEST.png":
                key = "INSTANT_HARVEST";
                break;
            case "assets/Item/Protect.png":
                key = "PROTECT";
                break;
            case "assets/Item/TRAP.png":
                key = "TRAP";
                break;

            // Handle Produk paths
            case "assets/Produk/daging_beruang.png":
                key = "DAGING_BERUANG";
                break;
            case "assets/Produk/daging_domba.png":
                key = "DAGING_DOMBA";
                break;
            case "assets/Produk/daging_kuda.png":
                key = "DAGING_KUDA";
                break;
            case "assets/Produk/JAGUNG.png":
                key = "JAGUNG";
                break;
            case "assets/Produk/LABU.png":
                key = "LABU";
                break;
            case "assets/Produk/SIRIP_HIU.png":
                key = "SIRIP_HIU";
                break;
            case "assets/Produk/STROBERI.png":
                key = "STROBERI";
                break;
            case "assets/Produk/susu.png":
                key = "SUSU";
                break;
            case "assets/Produk/telur.png":
                key = "TELUR";
                break;

            // Handle Tanaman paths
            case "assets/Tanaman/BIJI_JAGUNG.png":
                key = "BIJI_JAGUNG";
                break;
            case "assets/Tanaman/BIJI_LABU.png":
                key = "BIJI_LABU";
                break;
            case "assets/Tanaman/BIJI_STROBERI.png":
                key = "BIJI_STROBERI";
                break;
            case "assets/Hewan/Bear.png":
                key = "BERUANG";
                break;
            case "assets/Hewan/Chicken.png":
                key = "AYAM";
                break;
            case "assets/Hewan/Cow.png":
                key = "SAPI";
                break;
            case "assets/Hewan/Horse.png":
                key = "KUDA";
                break;
            case "assets/Hewan/Shark.png":
                key = "HIU_DARAT";
                break;

                case "assets/Hewan/shark fin.png":
                key = "HIU_DARAT";
                break;
            case "assets/Hewan/shark%20fin.png":
                key = "HIU_DARAT";
                break;
            case "assets/Hewan/Sheep.png":
                key = "DOMBA";
                break;
            case "assets/Produk/corn.png":
                key = "JAGUNG";
                break;
            case "assets/Produk/pumpkin.png":
                key = "LABU";
                break;
            case "assets/Produk/shark_fin.png":
                key = "SIRIP_HIU";
                break;
            case "assets/Produk/strawberry.png":
                key = "STROBERI";
                break;
            case "assets/Tanaman/corn seeds.png":
                key = "BIJI_JAGUNG";
                break;
            case "assets/Tanaman/corn%20seeds.png":
                key = "BIJI_JAGUNG";
                break;
            case "assets/Tanaman/pumpkin seeds.png":
                key = "BIJI_LABU";
                break;
            case "assets/Tanaman/strawberry seeds.png":
                key = "BIJI_STROBERI";
                break;
            case "assets/Item/bear%20trap.png":
                key = "TRAP";
                break;
            case "assets/Tanaman/pumpkin%20seeds.png":
                key = "BIJI_LABU";
                break;
            case "assets/Tanaman/strawberry%20seeds.png":
                key = "BIJI_STROBERI";
                break;
            case "assets/Item/bear trap.png":
                key = "TRAP";
                break;
            // Handle Hewan paths


            // Handle Item paths
            case "assets/Item/Instant Harvest.png":
                key = "INSTANT_HARVEST";
                break;
            case "assets/Produk/Daging Beruang.png":
                key = "DAGING_BERUANG";
                break;
            case "assets/Produk/Daging Domba.png":
                key = "DAGING_DOMBA";
                break;
            case "assets/Produk/Daging Kuda.png":
                key = "DAGING_KUDA";
                break;

            case "assets/Item/Instant%20Harvest.png":
                key = "INSTANT_HARVEST";
                break;
            case "assets/Produk/Daging%20Beruang.png":
                key = "DAGING_BERUANG";
                break;
            case "assets/Produk/Daging%20Domba.png":
                key = "DAGING_DOMBA";
                break;
            case "assets/Produk/Daging%20Kuda.png":
                key = "DAGING_KUDA";
                break;

            case "assets/Produk/shark fin.png":
                key = "SIRIP_HIU";
                break;
            case "assets/Produk/shark%20fin.png":
                key = "SIRIP_HIU";
                break;


            // Handle Tanaman paths
            case "assets/Tanaman/Corn_Seeds.png":
                key = "BIJI_JAGUNG";
                break;
            case "assets/Tanaman/Pumpkin_Seeds.png":
                key = "BIJI_LABU";
                break;
            case "assets/Tanaman/Strawberry_Seeds.png":
                key = "BIJI_STROBERI";
                break;


            default:
                key = "UNKNOWN";
                break;
        }
        return key;
    }


    public static String getRelativePathFromProject(String absolute_path) {
        String basePath = "org/bro/tubesoop2/";
        int index = absolute_path.indexOf(basePath);
        if (index == -1) {
            return "Path does not contain the base path."; // or handle this case as you see fit
        }
        return absolute_path.substring(index + basePath.length());
    }
}
