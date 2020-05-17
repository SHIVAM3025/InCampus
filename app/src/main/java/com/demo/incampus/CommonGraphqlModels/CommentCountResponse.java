package com.demo.incampus.CommonGraphqlModels;

public class CommentCountResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public String getCommentCount() {
        return data.getComments_aggregate().getAggregate().getCount();
    }

    public static class Data {
        private CommentsAggregate Comments_aggregate;

        public CommentsAggregate getComments_aggregate() {
            return Comments_aggregate;
        }
    }

    public static class CommentsAggregate {
        private Aggregate aggregate;

        public Aggregate getAggregate() {
            return aggregate;
        }
    }

    public static class Aggregate {
        private String count;

        public String getCount() {
            return count;
        }
    }

}
