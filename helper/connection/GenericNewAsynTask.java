package com.jbmatrix.utils.helper.connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jbmm0007 on 11/29/2016.
 */

public class GenericNewAsynTask  extends AsyncTask<String, Void, String> {

    private TaskListener taskListener;
    private String dialogMsg="", url="",requestMethod="",strAuth="",strPageCount;
    private ProgressDialog pd;
    private Context context;
    private JSONObject jsonObj=null;
    private HashMap<String, String> dataValue;
    private Boolean showDialog;

    public GenericNewAsynTask(String dialogMsg, Context context, String url, String requestMethod,
                           HashMap<String, String> dataValue,Boolean showDialog, String strAuth, String strPageCount) {
        Log.v("GenericAsynTask", "Hit");
        Log.e("Page count---->", strPageCount + "******");
        Log.e("Print auth rcvd ---->", strAuth + "<<<<<");
        this.dialogMsg = dialogMsg;
        this.context = context;
        this.url = url;
        this.requestMethod=requestMethod;
        //this.jsonObj=jsonObj;
        this.dataValue = dataValue;
        this.strAuth=strAuth;
        Log.e("Print auth set ---->", this.strAuth + "<<<<<");
        this.strPageCount=strPageCount;
        this.showDialog = showDialog;
        if (Connection.checkConnection(context))
        {
            execute();
        }else {
            Toast.makeText(context,"Check your internet connection",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPreExecute() {
        if(strPageCount.equals("")) {
            if (dialogMsg != null && !dialogMsg.equals("") && showDialog){
                pd = new ProgressDialog(context);
                pd.setMessage(dialogMsg);
                pd.show();
                //pd = ProgressDialog.show(context, "", dialogMsg);
                //pd.setProgressStyle(R.style.DialogTheme);
            }
        }
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        Log.e("DO IN BACKGROUND",">>>>>>>>>>>");
        String response = "";
        if(requestMethod.equals("POST"))
            response = Connection.performPostCall(url , dataValue, context);
        else if(requestMethod.equals("FILE_UPLOAD"))
            response = Connection.uploadFileToServer(url,dataValue);
        else if ((requestMethod.equals("GET")))
            response = Connection.performGetCall(url);

        Log.e(">>>>responseGen",response);
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        Log.e("ON POST EXECUTE",">>>>>>>>>>>");
        boolean flagSuccess=false;
        if(result!=null && !result.equals("")) {
            try {
                JSONObject jsonObj=new JSONObject(result);
                flagSuccess=true;
            } catch (Exception ex) {
                try {
                    JSONArray jsonArr=new JSONArray(result);
                    flagSuccess=true;
                }catch(Exception exp){
                }
            }
        }
        if(flagSuccess)
            taskListener.onSuccess(result);
        else
            taskListener.onFailure(result);
        if(strPageCount.equals("")) {
            try {
                if (showDialog && pd != null){
                    pd.dismiss();
                }
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static interface TaskListener {

        public void onSuccess(String success);

        public void onFailure(String error);
    }

    public void setOnTaskListener(TaskListener listener) {

        this.taskListener = listener;
    }

}
