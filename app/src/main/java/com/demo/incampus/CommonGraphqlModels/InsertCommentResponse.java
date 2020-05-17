package com.demo.incampus.CommonGraphqlModels;

import java.util.List;

public class InsertCommentResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        private InsertComments insert_Comments;

        public InsertComments getInsert_Comments() {
            return insert_Comments;
        }
    }

    public static class InsertComments {
        private int affected_rows;
        private List<ReturnResponse> returning;

        public int getAffected_rows() {
            return affected_rows;
        }

        public List<ReturnResponse> getReturning() {
            return returning;
        }
    }

    public static class ReturnResponse {
        private int id;
        private String content;

        public int getId() {
            return id;
        }

        public String getContent() {
            return content;
        }
    }

}
