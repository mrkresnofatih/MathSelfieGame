package com.mrkresnofatih.mathselfieapp.utilities;

import java.util.UUID;

public class GuidHelper {
    public static String GetNewGuid() {
        return UUID.randomUUID().toString();
    }
}
