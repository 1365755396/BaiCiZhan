package com.example.lin.myapplication.dao;

public class VocabDbSchema {
    public static final class VocabTable{
        public static final String NAME ="vocab_table";
        public static final class Cols {
            public static final String UUID="uuid";
            public static final String VOCAB="vocab";
            public static final String POS = "part_of_speech";
            public static final String MEANING = "meaning";
            public static final String EXAMPLE = "example";
            public static final String IMAGEID = "image_id";
            public static final String KILLED = "killed";


        }
    }
}
