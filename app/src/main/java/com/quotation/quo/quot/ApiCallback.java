package com.quotation.quo.quot;

/**
 * Created by Rezeq on 4/6/2018.
 * Email : rezeq.elewa@gmail.com
 */
public interface ApiCallback {

    void onSuccess( Object responseObject );

    void onFailure( String errorMsg );

}
