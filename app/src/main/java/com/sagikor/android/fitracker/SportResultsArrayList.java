package com.sagikor.android.fitracker;

import java.util.ArrayList;
import java.util.List;


public class SportResultsArrayList {

    List<SportCategoryNode> girlsGradesArrayList;
    List<SportCategoryNode> boysGradeArrayList;
    private static final int WORST_RESULT = 30;


    SportResultsArrayList() {
        girlsGradesArrayList = new ArrayList<>();
        boysGradeArrayList = new ArrayList<>();
        populateGirlsGradesList();
        populateBoysGradesList();

    }

    private void populateBoysGradesList() {
        //cubes |cubesRes  |abeRes  |aero  |aerRes   |abs |absRes |jump |jumpRes |hands |handsRes
        boysGradeArrayList.add( new SportCategoryNode( 8.8, 100, 05.55, 100, 97, 100,
                285, 100, 28, 100 ) );

        boysGradeArrayList.add( new SportCategoryNode( 8.8, 100, 05.58, 99, 96, 99,
                283, 99, 27, 99 ) );

        boysGradeArrayList.add( new SportCategoryNode( 8.9, 98, 06.01, 98, 95, 98,
                281, 98, 27, 99 ) );

        boysGradeArrayList.add( new SportCategoryNode( 8.9, 98, 06.05, 97, 94, 97,
                279, 97, 26, 97 ) );

        boysGradeArrayList.add( new SportCategoryNode( 8.9, 98, 06.09, 96, 93, 96,
                277, 96, 26, 97 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.0, 95, 06.13, 95, 92, 95,
                275, 95, 25, 95 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.0, 95, 06.17, 94, 91, 94,
                273, 94, 25, 95 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.0, 95, 06.22, 93, 90, 93,
                271, 93, 24, 93 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.1, 92, 06.27, 92, 89, 92,
                269, 92, 24, 93 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.1, 92, 06.32, 91, 88, 91,
                267, 91, 23, 91 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.1, 92, 06.37, 90, 87, 90,
                265, 90, 23, 91 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.2, 89, 06.41, 89, 85, 89,
                263, 89, 22, 89 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.2, 89, 06.49, 88, 84, 88,
                261, 88, 21, 88 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.2, 89, 06.55, 87, 83, 87,
                259, 87, 20, 87 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.3, 86, 07.01, 86, 82, 86,
                257, 86, 20, 87 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.3, 86, 07.07, 85, 81, 85,
                255, 85, 19, 85 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.4, 84, 07.13, 84, 80, 84,
                253, 84, 18, 84 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.4, 84, 07.19, 83, 78, 83,
                251, 83, 18, 84 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.5, 82, 07.25, 82, 77, 82,
                248, 82, 17, 82 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.5, 82, 07.31, 81, 76, 81,
                246, 81, 16, 81 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.6, 80, 07.37, 80, 75, 80,
                244, 80, 15, 80 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.6, 80, 07.43, 79, 74, 79,
                242, 79, 15, 80 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.7, 78, 07.49, 78, 73, 78,
                240, 78, 14, 78 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.7, 78, 07.55, 77, 71, 77,
                238, 77, 13, 77 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.8, 76, 08.02, 76, 70, 76,
                236, 76, 13, 77 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.8, 76, 08.08, 75, 69, 75,
                234, 75, 12, 75 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.9, 74, 08.14, 74, 68, 74,
                232, 74, 12, 75 ) );

        boysGradeArrayList.add( new SportCategoryNode( 9.9, 74, 08.20, 73, 67, 73,
                230, 73, 11, 73 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.0, 72, 08.26, 72, 65, 72,
                228, 72, 11, 73 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.0, 72, 08.32, 71, 64, 71,
                226, 71, 10, 71 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.1, 70, 08.36, 70, 63, 70,
                224, 70, 10, 71 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.2, 69, 08.44, 69, 62, 69,
                222, 69, 9, 69 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.2, 69, 08.50, 68, 61, 68,
                220, 68, 9, 69 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.3, 67, 08.58, 67, 60, 67,
                218, 67, 8, 67 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.3, 67, 09.02, 66, 58, 66,
                216, 66, 8, 67 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.4, 65, 09.08, 65, 57, 65,
                214, 65, 8, 67 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.4, 65, 09.14, 64, 56, 64,
                212, 64, 7, 64 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.5, 63, 09.21, 63, 55, 63,
                210, 63, 7, 64 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.5, 63, 09.27, 62, 54, 62,
                208, 62, 6, 62 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.6, 61, 09.33, 61, 53, 61,
                206, 61, 6, 62 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.7, 60, 09.39, 60, 51, 60,
                204, 60, 6, 62 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.7, 60, 09.45, 59, 50, 59,
                202, 59, 5, 59 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.8, 58, 09.51, 58, 49, 58,
                200, 58, 5, 59 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.8, 58, 09.57, 57, 48, 58,
                198, 57, 4, 57 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.9, 56, 10.03, 56, 47, 56,
                196, 56, 4, 57 ) );

        boysGradeArrayList.add( new SportCategoryNode( 10.9, 56, 10.09, 55, 46, 55,
                194, 55, 4, 57 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.0, 54, 10.15, 54, 44, 54,
                192, 54, 3, 54 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.0, 54, 10.21, 53, 43, 53,
                190, 53, 3, 54 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.1, 52, 10.27, 52, 42, 52,
                188, 52, 2, 52 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.2, 51, 10.34, 51, 41, 52,
                185, 51, 2, 52 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.2, 52, 10.40, 50, 40, 50,
                183, 50, 2, 52 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.3, 49, 10.46, 49, 39, 49,
                181, 49, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.3, 49, 10.52, 48, 37, 48,
                179, 48, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.4, 47, 10.58, 47, 36, 47,
                177, 47, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.4, 47, 11.04, 46, 35, 46,
                175, 46, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.5, 45, 11.10, 45, 34, 46,
                173, 45, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.5, 45, 11.16, 44, 33, 44,
                171, 44, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.6, 43, 11.22, 43, 32, 43,
                169, 43, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.7, 42, 11.28, 42, 31, 42,
                167, 42, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.7, 42, 11.34, 41, 30, 41,
                165, 41, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.8, 40, 11.40, 40, 29, 39,
                163, 40, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.8, 40, 11.46, 39, 28, 37,
                161, 39, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.9, 38, 11.53, 38, 28, 37,
                159, 38, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 11.9, 38, 11.59, 37, 27, 35,
                157, 37, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 12.0, 36, 12.05, 36, 27, 35,
                155, 36, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 12.0, 36, 12.11, 35, 26, 32,
                153, 35, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 12.1, 34, 12.17, 34, 26, 32,
                151, 34, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 12.2, 33, 12.23, 33, 25, 31,
                149, 33, 1, 49 ) );

        boysGradeArrayList.add( new SportCategoryNode( 12.2, 33, 12.29, 32, 25, 31,
                147, 32, 1, 49 ) );
    }

    private void populateGirlsGradesList() {
        //cubes |cubesRes  |abeRes  |aero  |aerRes   |abs |absRes |jump |jumpRes |hands |handsRes
        girlsGradesArrayList.add( new SportCategoryNode( 9.9, 100, 08.05, 100, 77, 100,
                236, 100, 1.30, 100 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 9.9, 100, 08.08, 99, 76, 99,
                234, 99, 1.26, 99 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.0, 98, 08.11, 98, 75, 98,
                232, 98, 1.22, 98 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.0, 98, 08.14, 97, 74, 97,
                230, 97, 1.19, 97 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.1, 96, 08.17, 96, 73, 96,
                228, 96, 1.16, 96 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.1, 96, 08.20, 95, 72, 95,
                226, 95, 1.13, 95 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.2, 94, 08.24, 94, 71, 94,
                224, 94, 1.10, 94 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.2, 94, 08.28, 93, 70, 93,
                222, 93, 1.08, 93 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.3, 92, 08.32, 92, 69, 92,
                220, 92, 1.06, 92 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.3, 92, 08.36, 91, 68, 91,
                218, 91, 1.04, 91 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.4, 90, 08.41, 90, 67, 90,
                216, 90, 1.02, 90 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.4, 90, 08.46, 89, 66, 89,
                214, 89, 1.0, 89 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.5, 88, 08.51, 88, 65, 88,
                212, 88, 0.58, 88 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.5, 88, 08.56, 87, 64, 87,
                210, 87, 0.57, 87 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.6, 86, 09.02, 86, 63, 86,
                208, 86, 0.55, 86 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.6, 86, 09.08, 85, 62, 85,
                206, 85, 0.53, 85 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.7, 84, 09.14, 84, 61, 84,
                204, 84, 0.51, 84 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.7, 84, 09.20, 83, 60, 83,
                202, 83, 0.5, 83 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.8, 82, 09.28, 82, 59, 82,
                200, 82, 0.48, 82 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.8, 82, 09.36, 81, 58, 81,
                199, 81, 0.46, 81 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 10.9, 80, 09.44, 80, 57, 80,
                197, 80, 0.45, 80 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.0, 79, 09.52, 79, 56, 79,
                195, 79, 0.43, 79 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.0, 79, 10.02, 78, 55, 78,
                193, 78, 0.41, 78 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.1, 77, 10.24, 77, 54, 77,
                191, 77, 0.39, 77 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.1, 77, 10.36, 76, 53, 76,
                190, 76, 0.38, 76 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.2, 75, 10.48, 75, 52, 75,
                188, 75, 0.36, 75 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.2, 75, 10.59, 74, 51, 74,
                186, 74, 0.34, 74 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.3, 73, 11.11, 73, 50, 73,
                184, 73, 0.33, 73 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.4, 72, 11.23, 72, 49, 72,
                182, 72, 0.31, 72 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.5, 72, 11.35, 71, 48, 71,
                181, 71, 0.29, 71 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.5, 72, 11.47, 70, 47, 70,
                179, 70, 0.28, 70 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.6, 69, 11.59, 69, 46, 69,
                177, 69, 0.26, 69 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.7, 68, 12.11, 68, 45, 68,
                175, 68, 0.24, 68 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.7, 68, 12.22, 67, 44, 67,
                174, 67, 0.22, 67 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.8, 66, 12.34, 66, 43, 66,
                172, 66, 0.21, 66 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.9, 65, 12.46, 65, 42, 65,
                170, 65, 0.19, 65 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 11.9, 65, 12.58, 64, 41, 64,
                168, 64, 0.18, 64 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.0, 63, 13.10, 63, 40, 63,
                166, 63, 0.17, 63 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.0, 63, 13.22, 62, 39, 62,
                165, 62, 0.16, 62 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.1, 61, 13.33, 61, 38, 61,
                163, 61, 0.15, 61 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.2, 60, 13.45, 60, 37, 60,
                161, 60, 0.14, 60 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.2, 60, 13.57, 59, 36, 59,
                159, 59, 0.13, 59 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.3, 58, 14.09, 58, 35, 58,
                157, 58, 0.13, 59 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.4, 57, 14.21, 57, 35, 58,
                156, 57, 0.12, 57 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.4, 57, 14.33, 56, 34, 56,
                154, 56, 0.12, 57 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.5, 55, 14.44, 55, 33, 55,
                152, 55, 0.11, 55 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.6, 54, 14.56, 54, 33, 55,
                150, 54, 0.11, 55 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.6, 54, 15.08, 53, 32, 53,
                148, 53, 0.10, 53 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.7, 52, 15.20, 52, 31, 52,
                147, 52, 0.10, 53 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.7, 52, 15.32, 51, 31, 52,
                145, 51, 0.09, 51 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.8, 50, 15.44, 50, 30, 50,
                143, 50, 0.09, 51 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.9, 49, 15.55, 49, 29, 49,
                141, 49, 0.08, 49 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 12.9, 49, 16.07, 48, 29, 49,
                139, 48, 0.08, 49 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.0, 47, 16.19, 47, 28, 47,
                138, 47, 0.07, 47 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.1, 46, 16.31, 46, 27, 46,
                136, 46, 0.07, 47 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.1, 46, 16.41, 45, 27, 46,
                134, 45, 0.07, 47 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.2, 44, 16.55, 44, 26, 44,
                132, 44, 0.06, 44 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.3, 43, 17.07, 43, 26, 44,
                131, 43, 0.06, 44 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.3, 43, 17.18, 42, 26, 44,
                129, 42, 0.06, 44 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.4, 41, 17.30, 41, 25, 41,
                127, 41, 0.05, 41 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.5, 40, 17.42, 40, 25, 41,
                125, 40, 0.05, 41 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.5, 40, 17.54, 39, 24, 39,
                123, 39, 0.05, 41 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.6, 38, 18.06, 38, 24, 39,
                122, 38, 0.04, 38 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.6, 38, 18.18, 37, 24, 39,
                120, 37, 0.04, 38 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.7, 36, 18.29, 36, 23, 36,
                118, 36, 0.04, 38 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.8, 35, 18.41, 35, 23, 36,
                116, 35, 0.03, 35 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.8, 35, 18.53, 34, 23, 36,
                114, 34, 0.03, 35 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 13.9, 33, 19.05, 33, 22, 33,
                113, 33, 0.03, 35 ) );

        girlsGradesArrayList.add( new SportCategoryNode( 14.0, 32, 19.17, 32, 22, 33,
                111, 32, 0.02, 32 ) );
    }

    public int getGirlsCubesResult( double score ) {
        return getCubesResult( girlsGradesArrayList, score );
    }

    public int getBoysCubesResult( double score ) {
        return getCubesResult( boysGradeArrayList, score );
    }

    private int getCubesResult( List<SportCategoryNode> arrayList, double score ) {
        SportCategoryNode ptr;
        boolean emptyField = score == 0;
        for ( int i = 0 ; i < arrayList.size() ; i++ ) {
            ptr = arrayList.get( i );
            if ( ptr.getCubesScore() >= score ) {
                return emptyField ? WORST_RESULT : ptr.getCubesResult();
            }
        }
        return WORST_RESULT;
    }

    public int getGirlsAerobicResult( double score ) {
        return getAerobicResult( girlsGradesArrayList, score );
    }

    public int getBoysAerobicResult( double score ) {
        return getAerobicResult( boysGradeArrayList, score );
    }

    private int getAerobicResult( List<SportCategoryNode> arrayList, double score ) {
        SportCategoryNode ptr;
        boolean emptyField = score == 0;
        for ( int i = 0 ; i < arrayList.size() ; i++ ) {
            ptr = arrayList.get( i );
            if ( ptr.getAerobicScore() >= score ) {
                return emptyField ? WORST_RESULT : ptr.getAerobicResult();
            }
        }
        return WORST_RESULT;
    }

    public int getGirlsSitUpAbsResult( int score ) {
        return getAbsResult( girlsGradesArrayList, score );
    }

    public int getBoysSitUpAbsResult( int score ) {
        return getAbsResult( boysGradeArrayList, score );
    }

    private int getAbsResult( List<SportCategoryNode> arrayList, int score ) {
        SportCategoryNode ptr;
        for ( int i = 0 ; i < arrayList.size() ; i++ ) {
            ptr = arrayList.get( i );
            if ( score >= ptr.getAbsScore() ) {
                return ptr.getAbsResult();
            }
        }
        return WORST_RESULT;
    }

    //TODO ISSUE 1183 - WAITING FOR CLIENT SCORES TABLE FOR THIS EXAM.
//    public int getPlankAbsResult( int score ) {
//
//    }

    public int getGirlsStaticHandsResult( double score ) {
        return getHandsResult( girlsGradesArrayList, score );
    }

    //TODO ISSUE 1183 - WAITING FOR CLIENT SCORES TABLE FOR THIS EXAM.
//    public int getPushUpHandsResult( double score ) {
//
//    }

    public int getBoysHandsResult( double score ) {
        return getHandsResult( boysGradeArrayList, score );
    }

    private int getHandsResult( List<SportCategoryNode> ArrayList, double score ) {
        SportCategoryNode ptr;
        for ( int i = 0 ; i < ArrayList.size() ; i++ ) {
            ptr = ArrayList.get( i );
            if ( score >= ptr.getHandsScore() ) {
                return ptr.getHandsResult();
            }
        }
        return WORST_RESULT;
    }

    public int getGirlsJumpResult( int score ) {
        return getJumpResult( girlsGradesArrayList, score );
    }

    public int getBoysJumpResult( int score ) {
        return getJumpResult( boysGradeArrayList, score );
    }

    public int getJumpResult( List<SportCategoryNode> arrayList, int score ) {

        SportCategoryNode ptr;
        for ( int i = 0 ; i < arrayList.size() ; i++ ) {
            ptr = arrayList.get( i );
            if ( score >= ptr.getJumpScore() ) {
                return ptr.getJumpResult();
            }
        }
        return WORST_RESULT;
    }
}
