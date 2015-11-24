package com.jskz.listviewnetworkdemo;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.jskz.listviewnetworkdemo.model.DataBean;
import com.jskz.listviewnetworkdemo.model.WordBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener {
	
	//===================//
	private Button mSearchBtn;
	private EditText mPoint;
	private ListView mList;
	private TextView mTvShow;
	private Button mSelectallBtn;  
    private Button mCancelBtn;  
    private Button mDeselectallBtn;  
    
    //===================//
    private int mCheckNum;
    private String Baiduurl = "http://apis.baidu.com/netpopo/idiom/chengyu";
    private boolean mSwitch;
    
    //===================//
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("中国成语字典");
        initView();
        initListener();
    }

	private void initView() {
		
		this.mPoint = (EditText) this.findViewById(R.id.aci_point_et);
		this.mSearchBtn = (Button) this.findViewById(R.id.aci_search_btn);
		this.mList = (ListView) this.findViewById(R.id.aci_list_lv);
	}
	
	private void initListener() {
		
		mPoint.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);	
		
	}	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.aci_search_btn:
			SearchWord();
			break;
		}
		
	}
		
	public void SearchWord(){
		String url = Baiduurl + "?keyword=" + mPoint.getText().toString() + "&appkey=1307ee261de8bbcf83830de89caae73f";
		HttpUtils http = new HttpUtils();
		RequestParams params = new RequestParams(); 
		params.addHeader("apikey", "6c36e1ebba98b1c157d34cfe81c5ef3e");
		http.send(HttpRequest.HttpMethod.GET,url,params,
		    new RequestCallBack<String>(){

		        @Override
		        public void onSuccess(ResponseInfo<String> responseInfo) {
		            String mdata = responseInfo.result.toString();
		            Toast.makeText(getApplicationContext(), "查询成功", Toast.LENGTH_SHORT).show();
		            Log.e("Success", mdata);
		            
		            WordBean wordBean = JSON.parseObject(mdata, WordBean.class);
		            List<DataBean> list = wordBean.getData();
		            for(int i=0; i < (list.size()); i++) {
		            	list.get(i).setType(i % 2 == 0? 0:1);
	            		/*if(i % 2 == 0) {
	            			list.get(i).setType(0);
	            		} else {
	            			list.get(i).setType(1);
	            		}*/
	            	}
		            final ListAdapter listAdapter = new ListAdapter(MainActivity.this, list);
	            	mList.setAdapter(listAdapter);	
	            	mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	                    @Override
	                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                    	listAdapter.setCurrentItem(position);
	                    	listAdapter.notifyDataSetChanged();
	                    }
	                });
		        }

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.e("Failure" , arg0.getMessage() + " " + arg1);
				}		       
		});
		
		
	}


	
		
}
	
