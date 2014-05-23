/* ==========================================
 * CategorizeUserForum : a free Java graph-theory library
 * ==========================================
 * 
 * salmuz : Carranza Alarcon Yonatan Carlos
 * 
 * (C) Copyright 2014, by salmuz and Contributors.
 * 
 * Project Info:  https://github.com/salmuz/Graphes_Multi_Plateformes
 * Project Creator:  salmuz (https://www.assembla.com/spaces/salmuz-java) 
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc.,
 * 
 * ------------------
 * Point.java
 * ------------------
 * (C) Copyright 2014, by salmuz and Contributors
 *
 * Original Author: Carranza Alarcon Yonatan Carlos
 * Contributor(s):  
 *
 * Changes
 * -------
 *
 */

package org.montp2.m1decol.ter.utils;

import java.util.Arrays;
import java.util.List;

public final class Constants {

    public static final String JDBC_SEPARATOR = "$";

    public static final String HOME_TREETAGER = "/Users/user/Applications/tree-tagger/";

    public static final String TREETAGGER_LANGUAGE = "/Users/user/Applications/tree-tagger/lib/french-utf8.par";

    public static final List<String> tagsINV = Arrays.asList(new String[]{"PUN", "NUM", "PRP", "SENT", "ADV"});

    public static final List<String> subtagsINV = Arrays.asList(new String[]{"DEM","PER","REL","POS"});


    public static final List<String> userEmpty = Arrays.asList(new String[]{
            "user_640.txt","user_277.txt","user_61.txt","user_646.txt","user_364"
            ,"user_347.txt","user_398.txt"// vide
    });

    // il faut l'extraire de la base de données, on l'a fait avec python :)
    public static final List<String> userExclude = Arrays.asList(new String[]{
            //2
            "user_36.txt","user_37.txt","user_38.txt","user_46.txt","user_52.txt","user_54.txt","user_58.txt","user_64.txt",
            "user_69.txt","user_71.txt","user_72.txt","user_77.txt","user_78.txt","user_81.txt","user_95.txt","user_100.txt",
            "user_102.txt","user_105.txt","user_106.txt","user_107.txt","user_122.txt","user_130.txt","user_134.txt","user_137.txt",
            "user_150.txt","user_156.txt","user_163.txt","user_169.txt","user_173.txt","user_183.txt","user_186.txt","user_197.txt",
            "user_202.txt","user_207.txt","user_212.txt","user_224.txt","user_225.txt","user_229.txt","user_230.txt","user_240.txt",
            "user_246.txt","user_254.txt","user_256.txt","user_265.txt","user_267.txt","user_273.txt","user_275.txt","user_280.txt",
            "user_284.txt","user_303.txt","user_306.txt","user_313.txt","user_314.txt","user_317.txt","user_319.txt","user_320.txt",
            "user_327.txt","user_332.txt","user_338.txt","user_343.txt","user_346.txt","user_351.txt","user_358.txt","user_361.txt",
            "user_362.txt","user_363.txt","user_368.txt","user_375.txt","user_378.txt","user_383.txt","user_384.txt","user_388.txt",
            "user_389.txt","user_395.txt","user_396.txt","user_415.txt","user_417.txt","user_426.txt","user_428.txt","user_429.txt",
            "user_434.txt","user_435.txt","user_436.txt","user_444.txt","user_447.txt","user_448.txt","user_457.txt","user_461.txt",
            "user_462.txt","user_473.txt","user_475.txt","user_476.txt","user_480.txt","user_484.txt","user_487.txt","user_499.txt",
            "user_501.txt","user_505.txt","user_534.txt","user_540.txt","user_566.txt","user_582.txt","user_586.txt","user_590.txt",
            "user_591.txt","user_595.txt","user_597.txt","user_605.txt","user_616.txt","user_617.txt","user_621.txt","user_654.txt",
            "user_664.txt",
            //3
            "user_23.txt","user_24.txt","user_28.txt","user_43.txt","user_47.txt","user_66.txt","user_73.txt","user_75.txt",
            "user_98.txt","user_126.txt","user_132.txt","user_139.txt","user_143.txt","user_144.txt","user_148.txt","user_153.txt",
            "user_167.txt","user_170.txt","user_218.txt","user_220.txt","user_221.txt","user_226.txt","user_227.txt","user_232.txt",
            "user_233.txt","user_236.txt","user_241.txt","user_248.txt","user_251.txt","user_272.txt","user_283.txt","user_295.txt",
            "user_302.txt","user_307.txt","user_321.txt","user_325.txt","user_329.txt","user_334.txt","user_340.txt","user_369.txt",
            "user_387.txt","user_420.txt","user_504.txt","user_527.txt","user_535.txt","user_549.txt","user_578.txt","user_604.txt",
            "user_607.txt","user_615.txt",
            //4
            "user_15.txt","user_18.txt","user_35.txt","user_41.txt","user_42.txt","user_49.txt","user_57.txt","user_67.txt",
            "user_70.txt","user_82.txt","user_84.txt","user_91.txt","user_92.txt","user_99.txt","user_120.txt","user_121.txt",
            "user_135.txt","user_141.txt","user_146.txt","user_158.txt","user_159.txt","user_184.txt","user_193.txt","user_196.txt",
            "user_199.txt","user_201.txt","user_208.txt","user_237.txt","user_266.txt","user_268.txt","user_298.txt","user_304.txt",
            "user_310.txt","user_311.txt","user_312.txt","user_323.txt","user_330.txt","user_341.txt","user_356.txt","user_402.txt",
            "user_463.txt","user_506.txt","user_509.txt","user_546.txt",
            // 5
            "user_4.txt","user_40.txt","user_44.txt","user_65.txt","user_86.txt","user_88.txt","user_90.txt","user_97.txt",
            "user_115.txt","user_119.txt","user_124.txt","user_149.txt","user_160.txt","user_161.txt","user_162.txt","user_166.txt",
            "user_189.txt","user_200.txt","user_215.txt","user_234.txt","user_238.txt","user_239.txt","user_261.txt","user_296.txt",
            "user_308.txt","user_318.txt","user_345.txt","user_350.txt",
            // 6
            "user_51.txt","user_62.txt","user_85.txt","user_93.txt","user_111.txt","user_116.txt","user_117.txt","user_123.txt",
            "user_164.txt","user_174.txt","user_180.txt","user_219.txt","user_223.txt","user_305.txt","user_326.txt","user_336.txt",
            // 7
            "user_17.txt","user_20.txt","user_48.txt","user_53.txt","user_104.txt","user_140.txt","user_203.txt","user_324.txt","user_342.txt", //7
            "user_25.txt","user_29.txt","user_39.txt","user_79.txt","user_101.txt","user_145.txt","user_151.txt", //8
            "user_19.txt","user_45.txt","user_50.txt","user_114.txt","user_152.txt","user_154.txt","user_188.txt", //9
            "user_1.txt","user_2.txt","user_6.txt","user_9.txt","user_12.txt","user_21.txt","user_27.txt","user_168.txt","user_209.txt", //10
            "user_3.txt","user_7.txt","user_10.txt","user_13.txt","user_14.txt","user_22.txt","user_30.txt", // 11
            "user_5.txt","user_8.txt","user_11.txt","user_31.txt","user_32.txt","user_33.txt","user_34.txt", // 12

             });
}
