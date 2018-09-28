package com.sagiKor1193.android.fitracker;

import java.util.ArrayList;

public class SportResultsList {


    ArrayList<SportCategoryNode> list;
    private final int WORST_RESULT = 30;



    public SportResultsList(){
         list = new ArrayList<>();

                                      //cubes |cubesRes  |abeRes  |aero  |aerRes   |abs |absRes |jump |jumpRes |hands |handsRes
        list.add(new SportCategoryNode(9.9, 100 , 08.05, 100, 77, 100,
                236, 100 , 1.30, 100));

        list.add(new SportCategoryNode(9.9, 100 , 08.08, 99, 76, 99,
                234, 99 , 1.26, 99));

        list.add(new SportCategoryNode(10.0, 98 , 08.11, 98, 75, 98,
                232, 98 , 1.22, 98));

        list.add(new SportCategoryNode(10.0, 98 , 08.14, 97, 74, 97,
                230, 97 , 1.19, 97));

        list.add(new SportCategoryNode(10.1, 96 , 08.17, 96, 73, 96,
                228, 96 , 1.16, 96));

        list.add(new SportCategoryNode(10.1, 96 , 08.20, 95, 72, 95,
                226, 95 , 1.13, 95));

        list.add(new SportCategoryNode(10.2, 94 , 08.24, 94, 71, 94,
                224, 94 , 1.10, 94));

        list.add(new SportCategoryNode(10.2, 94 , 08.28, 93, 70, 93,
                222, 93 , 1.08, 93));

        list.add(new SportCategoryNode(10.3, 92 , 08.32, 92, 69, 92,
                220, 92 , 1.06, 92));

        list.add(new SportCategoryNode(10.3, 92 , 08.36, 91, 68, 91,
                218, 91 , 1.04, 91));

        list.add(new SportCategoryNode(10.4, 90 , 08.41, 90, 67, 90,
                216, 90 , 1.02, 90));

        list.add(new SportCategoryNode(10.4, 90 , 08.46, 89, 66, 89,
                214, 89 , 1.0, 89));

        list.add(new SportCategoryNode(10.5, 88 , 08.51, 88, 65, 88,
                212, 88 , 0.58, 88));

        list.add(new SportCategoryNode(10.5, 88 , 08.56, 87, 64, 87,
                210, 87 , 0.57, 87));

        list.add(new SportCategoryNode(10.6, 86 , 09.02, 86, 63, 86,
                208, 86 , 0.55, 86));

        list.add(new SportCategoryNode(10.6, 86 , 09.08, 85, 62, 85,
                206, 85 , 0.53, 85));

        list.add(new SportCategoryNode(10.7, 84 , 09.14, 84, 61, 84,
                204, 84 , 0.51, 84));

        list.add(new SportCategoryNode(10.7, 84 , 09.20, 83, 60, 83,
                202, 83 , 0.5, 83));

        list.add(new SportCategoryNode(10.8, 82 , 09.28, 82, 59, 82,
                200, 82 , 0.48, 82));

        list.add(new SportCategoryNode(10.8, 82 , 09.36, 81, 58, 81,
                199, 81 , 0.46, 81));

        list.add(new SportCategoryNode(10.9, 80 , 09.44, 80, 57, 80,
                197, 80 , 0.45, 80));

        list.add(new SportCategoryNode(11.0, 79 , 09.52, 79, 56, 79,
                195, 79 , 0.43, 79));

        list.add(new SportCategoryNode(11.0, 79 , 10.02, 78, 55, 78,
                193, 78 , 0.41, 78));

        list.add(new SportCategoryNode(11.1, 77 , 10.24, 77, 54, 77,
                191, 77 , 0.39, 77));

        list.add(new SportCategoryNode(11.1, 77 , 10.36, 76, 53, 76,
                190, 76 , 0.38, 76));

        list.add(new SportCategoryNode(11.2, 75 , 10.48, 75, 52, 75,
                188, 75 , 0.36, 75));

        list.add(new SportCategoryNode(11.2, 75 , 10.59, 74, 51, 74,
                186, 74 , 0.34, 74));

        list.add(new SportCategoryNode(11.3, 73 , 11.11, 73, 50, 73,
                184, 73 , 0.33, 73));

        list.add(new SportCategoryNode(11.4, 72 , 11.23, 72, 49, 72,
                182, 72 , 0.31, 72));

        list.add(new SportCategoryNode(11.5, 72 , 11.35, 71, 48, 71,
                181, 71 , 0.29, 71));

        list.add(new SportCategoryNode(11.5, 72 , 11.47, 70, 47, 70,
                179, 70 , 0.28, 70));

        list.add(new SportCategoryNode(11.6, 69 , 11.59, 69, 46, 69,
                177, 69 , 0.26, 69));

        list.add(new SportCategoryNode(11.7, 68 , 12.11, 68, 45, 68,
                175, 68 , 0.24, 68));

        list.add(new SportCategoryNode(11.7, 68 , 12.22, 67, 44, 67,
                174, 67 , 0.22, 67));

        list.add(new SportCategoryNode(11.8, 66 , 12.34, 66, 43, 66,
                172, 66 , 0.21, 66));

        list.add(new SportCategoryNode(11.9, 65 , 12.46, 65, 42, 65,
                170, 65 , 0.19, 65));

        list.add(new SportCategoryNode(11.9, 65 , 12.58, 64, 41, 64,
                168, 64 , 0.18, 64));

        list.add(new SportCategoryNode(12.0, 63 , 13.10, 63, 40, 63,
                166, 63 , 0.17, 63));

        list.add(new SportCategoryNode(12.0, 63 , 13.22, 62, 39, 62,
                165, 62 , 0.16, 62));

        list.add(new SportCategoryNode(12.1, 61 , 13.33, 61, 38, 61,
                163, 61 , 0.15, 61));

        list.add(new SportCategoryNode(12.2, 60 , 13.45, 60, 37, 60,
                161, 60 , 0.14, 60));

        list.add(new SportCategoryNode(12.2, 60 , 13.57, 59, 36, 59,
                159, 59 , 0.13, 59));

        list.add(new SportCategoryNode(12.3, 58 , 14.09, 58, 35, 58,
                157, 58 , 0.13, 59));

        list.add(new SportCategoryNode(12.4, 57 , 14.21, 57, 35, 58,
                156, 57 , 0.12, 57));

        list.add(new SportCategoryNode(12.4, 57 , 14.33, 56, 34, 56,
                154, 56 , 0.12, 57));

        list.add(new SportCategoryNode(12.5, 55 , 14.44, 55, 33, 55,
                152, 55 , 0.11, 55));

        list.add(new SportCategoryNode(12.6, 54 , 14.56, 54, 33, 55,
                150, 54 , 0.11, 55));

        list.add(new SportCategoryNode(12.6, 54 , 15.08, 53, 32, 53,
                148, 53 , 0.10, 53));

        list.add(new SportCategoryNode(12.7, 52 , 15.20, 52, 31, 52,
                147, 52 , 0.10, 53));

        list.add(new SportCategoryNode(12.7, 52 , 15.32, 51, 31, 52,
                145, 51 , 0.09, 51));

        list.add(new SportCategoryNode(12.8, 50 , 15.44, 50, 30, 50,
                143, 50 , 0.09, 51));

        list.add(new SportCategoryNode(12.9, 49 , 15.55, 49, 29, 49,
                141, 49 , 0.08, 49));

        list.add(new SportCategoryNode(12.9, 49 , 16.07, 48, 29, 49,
                139, 48 , 0.08, 49));

        list.add(new SportCategoryNode(13.0, 47 , 16.19, 47, 28, 47,
                138, 47 , 0.07, 47));

        list.add(new SportCategoryNode(13.1, 46 , 16.31, 46, 27, 46,
                136, 46 , 0.07, 47));

        list.add(new SportCategoryNode(13.1, 46 , 16.41, 45, 27, 46,
                134, 45 , 0.07, 47));

        list.add(new SportCategoryNode(13.2, 44 , 16.55, 44, 26, 44,
                132, 44 , 0.06, 44));

        list.add(new SportCategoryNode(13.3, 43 , 17.07, 43, 26, 44,
                131, 43 , 0.06, 44));

        list.add(new SportCategoryNode(13.3, 43 , 17.18, 42, 26, 44,
                129, 42 , 0.06, 44));

        list.add(new SportCategoryNode(13.4, 41 , 17.30, 41, 25, 41,
                127, 41 , 0.05, 41));

        list.add(new SportCategoryNode(13.5, 40 , 17.42, 40, 25, 41,
                125, 40 , 0.05, 41));

        list.add(new SportCategoryNode(13.5, 40 , 17.54, 39, 24, 39,
                123, 39 , 0.05, 41));

        list.add(new SportCategoryNode(13.6, 38 , 18.06, 38, 24, 39,
                122, 38 , 0.04, 38));

        list.add(new SportCategoryNode(13.6, 38 , 18.18, 37, 24, 39,
                120, 37 , 0.04, 38));

        list.add(new SportCategoryNode(13.7, 36 , 18.29, 36, 23, 36,
                118, 36 , 0.04, 38));

        list.add(new SportCategoryNode(13.8, 35 , 18.41, 35, 23, 36,
                116, 35 , 0.03, 35));

        list.add(new SportCategoryNode(13.8, 35 , 18.53, 34, 23, 36,
                114, 34 , 0.03, 35));

        list.add(new SportCategoryNode(13.9, 33 , 19.05, 33, 22, 33,
                113, 33 , 0.03, 35));

        list.add(new SportCategoryNode(14.0, 32 , 19.17, 32, 22, 33,
                111, 32 , 0.02, 32));


    }

    public int getCubesResult(double score){
        SportCategoryNode ptr;
        for(int i = 0; i < list.size();i++){
            ptr = list.get(i);
            if( ptr.get_cubesData() >= score ){
                return ptr.get_cubesRes();
            }
        }
        return WORST_RESULT;

    }
    public int getAeroResult(double score){
        SportCategoryNode ptr;
        for(int i = 0; i < list.size(); i++){
            ptr = list.get(i);
            if( ptr.get_aeroData() >= score  ){
                return ptr.get_aeroRes();
            }
        }
        return WORST_RESULT;

    }
    public int getAbsResult(int score){
        SportCategoryNode ptr;
        for(int i = 0; i < list.size(); i++){
            ptr = list.get(i);
            if( score >=  ptr.get_absData() ){
                return ptr.get_absRes();
            }
        }
        return WORST_RESULT;

    }
    public int getHandsResult(double score){
        SportCategoryNode ptr;
        for(int i = 0; i < list.size(); i++){
            ptr = list.get(i);
            if( score >= ptr.get_handsData() ){
                return ptr.get_handsRes();
            }
        }
        return WORST_RESULT;
    }
    public int geJumpResult(int score){
        SportCategoryNode ptr;
        for(int i = 0; i < list.size(); i++){
            ptr = list.get(i);
            if( score >= ptr.get_jumpData() ){
                return ptr.get_jumpRes();
            }
        }
        return WORST_RESULT;

    }
}
