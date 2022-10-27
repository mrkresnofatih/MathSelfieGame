package com.mrkresnofatih.mathselfieapp.utilities;

public class Constants {
    public static class DynamoDb
    {
        public static final String DynamoDbTableName = "MathSelfieGameTable";

        public static class DynamoDbKeyFormats
        {
            public static final String ProblemPartitionKey = "problem-set#%s";
            public static final String ProblemSortKey = "problem#%s";
        }
    }

    public static class S3
    {
        public static final String S3BucketName = "math-selfie-game-bucket";
    }

    public static class SQS
    {
        public static final String SQSQueueName = "mathselfieappqueue";
    }
}
