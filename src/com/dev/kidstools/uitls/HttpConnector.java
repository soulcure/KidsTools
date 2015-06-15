package com.dev.kidstools.uitls;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class HttpConnector {

    private static final String TAG = HttpConnector.class.getSimpleName();


    private HttpConnector() {
        throw new AssertionError();
    }

    /**
     * AsyncTask to get data by HttpRequest
     *
     * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
     * 2013-11-15
     */

    public static void httpGet(String url, IGetListener request) {
        new HttpGetAsyncTask(request).execute(url);
    }

    public static void httpPost(String url, String postStr,
                                IPostListener request) {
        new HttpPostAsyncTask(request).execute(url, postStr);
    }

    private static class HttpGetAsyncTask extends
            AsyncTask<String, Void, HttpResponse> {

        private IGetListener mRequest;

        public HttpGetAsyncTask(IGetListener request) {
            mRequest = request;
        }

        @Override
        protected HttpResponse doInBackground(String... params) {

            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setSoTimeout(httpParams, 30000);
            HttpClient httpClient = new DefaultHttpClient(httpParams);

            String httpUrl = params[0];

            try {

                HttpGet httpGet = new HttpGet(httpUrl);

                HttpResponse httpResponse = httpClient.execute(httpGet);

                return httpResponse;
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;

        }

        protected void onPostExecute(HttpResponse httpResponse) {

            if (httpResponse == null) {
                mRequest.doError(new Exception("HttpGetAsyncTask error"));
                return;
            }
            try {
                String response = EntityUtils.toString(
                        httpResponse.getEntity(), HTTP.UTF_8);
                mRequest.httpReqResult(response);
            } catch (IOException e) {
                mRequest.doError(e);
            }

        }

    }

    private static class HttpPostAsyncTask extends
            AsyncTask<String, Void, String> {

        private IPostListener mRequest;

        public HttpPostAsyncTask(IPostListener request) {
            mRequest = request;
        }

        @Override
        protected String doInBackground(String... params) {

            String httpUrl = params[0];
            String postStr = params[1];

            if (StringUtils.isEmpty(httpUrl)
                    || StringUtils.isEmpty(postStr)) {
                return null;
            }

            return doPostString(httpUrl, postStr);

        }

        protected void onPostExecute(String response) {

            if (StringUtils.isEmpty(response)) {
                mRequest.doError();
            } else {
                mRequest.httpReqResult(response);
            }


        }

    }

    /**
     * 发送请求
     *
     * @param path
     * @param strJson 请求参数
     * @return 请求结果
     */
    public static String doPostString(String path, String strJson) {
        String response = null;
        try {
            Log.v(TAG, path + " : post content = " + strJson);

            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(30000); // 链接超时
            connection.setReadTimeout(20000); // 读取超时

            connection.setDoOutput(true); // 是否输入参数
            connection.setDoInput(true); // 是否向HttpUrLConnection读入，默认true.
            connection.setRequestMethod("POST"); // 提交模式
            connection.setUseCaches(false); // 不能缓存
            connection.setInstanceFollowRedirects(true); // 连接是否尊重重定向
            connection.setRequestProperty("Content-Type", "application/json"); // 设定传入内容是可序列化的java对象
            connection.connect(); // 连接，所有connection的配置信息必须在connect()方法前提交。

            OutputStream os = connection.getOutputStream();

            byte[] content = strJson.getBytes("utf-8");
            os.write(content, 0, content.length);
            os.flush();
            os.close();

            //服务器修改，错误类型封装到了 response json code里面，此处取消 http ResponseCode 判断
            /*int code = connection.getResponseCode();
            if (code == 200) {
                response = StreamUtils.readStream(connection.getInputStream());
                Log.v(TAG, "http post response:" + response);
            } else {
                Log.e(TAG, "http post :" + path + "&error  code:" + code);
            }*/

            response = StreamUtils.readStream(connection.getInputStream());
            Log.v(TAG, path + " : post response = " + response);

            connection.disconnect();


        } catch (Exception e) {
            Log.e(TAG, path + " : post Exception = " + e.toString());
            //e.printStackTrace();
        }
        return response;
    }

}
