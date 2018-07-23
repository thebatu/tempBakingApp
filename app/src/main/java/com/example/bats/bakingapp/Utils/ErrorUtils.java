package com.example.bats.bakingapp.Utils;

import com.example.bats.bakingapp.Activities.MainActivity;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * in case retrofit error parse and convert the error to readable format used with ApiError
 *
 */
public class ErrorUtils {

    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                MainActivity.retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try{
            error = converter.convert(response.errorBody());
        }
        catch (IOException e){
            return new ApiError();
        }
        return error;

    }
}
