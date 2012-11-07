package com.example.theone;

import java.io.OutputStream;
import java.lang.reflect.Method;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity {

	Button button;
	
	private static final String TAG = MainActivity.class.getCanonicalName();
	
	private static final int BARREL_LEFT  = 1;
	private static final int BARREL_RIGHT = 2;
	
	private static final int MENU_CONNECT = 1;
	
	private RadioGroup radioModeGroup;
	private RadioButton radioSexButton;
	private BluetoothDevice m_Cannon;
	private ConnectTask		m_ConTask;
	private boolean 		m_isConnected = false;
	
    private BluetoothSocket m_BtSocket;
    private OutputStream 	m_Output;
   

    private FireTask		m_FireTask;
	SeekBar seekbarred;
	SeekBar seekbargreen;
	SeekBar seekbarblue;
	SeekBar seekbardelay;
	TextView valueRed;
	TextView valueGreen;
	TextView valueBlue;
	TextView valueDelay;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
        seekbarred.setVisibility(View.GONE);
        seekbarblue.setVisibility(View.GONE);
        seekbargreen.setVisibility(View.GONE);
    }
    
    public void addListenerOnButton() {
    	button=(Button) findViewById(R.id.button1);
    	radioModeGroup=(RadioGroup) findViewById(R.id.radioSex);
    	button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent browserIntent = 
//                        new Intent(Intent.ACTION_VIEW, Uri.parse("http://about.me/the1pawan"));
//				startActivity(browserIntent);
				
				//hardware device
				//m_Cannon = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("00:19:A4:02:44:2A");
				//Android tab
				//m_Cannon = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("A0:75:91:53:6F:2F");
				//Murali PC
				m_Cannon = BluetoothAdapter.getDefaultAdapter().getRemoteDevice("00:15:83:15:A3:10");
		        
		        connect();
				
			}
		});
    	
    	radioModeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if (checkedId==R.id.radioMode0) {
					seekbarred.setVisibility(View.GONE);
			        seekbarblue.setVisibility(View.GONE);
			        seekbargreen.setVisibility(View.GONE);
			        valueRed.setText(" value is 0");
			        valueBlue.setText(" value is 0");
			        valueGreen.setText(" value is 0");
					Toast.makeText(MainActivity.this,"Mode0", Toast.LENGTH_SHORT).show();
					fire(1);
				}
				else if (checkedId==R.id.radioMode1) {
					seekbarred.setVisibility(View.GONE);
			        seekbarblue.setVisibility(View.GONE);
			        seekbargreen.setVisibility(View.GONE);
			        valueRed.setText(" value is 0");
			        valueBlue.setText(" value is 0");
			        valueGreen.setText(" value is 0");
					Toast.makeText(MainActivity.this,"Mode1", Toast.LENGTH_SHORT).show();
				} 
				else if (checkedId==R.id.radioMode2) {
					seekbarred.setVisibility(View.GONE);
			        seekbarblue.setVisibility(View.GONE);
			        seekbargreen.setVisibility(View.GONE);
			        valueRed.setText(" value is 0");
			        valueBlue.setText(" value is 0");
			        valueGreen.setText(" value is 0");
					Toast.makeText(MainActivity.this,"Mode2", Toast.LENGTH_SHORT).show();
				}
				else if (checkedId==R.id.radioMode3) {
					Toast.makeText(MainActivity.this,"Mode3", Toast.LENGTH_SHORT).show();
					seekbarred.setVisibility(View.VISIBLE);
			        seekbarblue.setVisibility(View.VISIBLE);
			        seekbargreen.setVisibility(View.VISIBLE);
				}
			}
		});
    	
    	 seekbarred = (SeekBar) findViewById(R.id.seekBarRed);
    	 valueRed=(TextView) findViewById(R.id.textViewRedvalue);
    	 seekbarred.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
    	 
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				valueRed.setText(" value is "+progress);
			}
		});
    	 
    	 seekbargreen=(SeekBar) findViewById(R.id.seekBarGreen);
    	 valueGreen=(TextView) findViewById(R.id.textViewGreenvalue);
    	 seekbargreen.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				valueGreen.setText(" value is "+progress);
			}
		});
    	 
    	 seekbarblue=(SeekBar) findViewById(R.id.seekBarBlue);
    	 valueBlue=(TextView) findViewById(R.id.textViewBluevalue);
    	 seekbarblue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				valueBlue.setText(" value is "+progress);
			}
		});
    	 
    	 seekbardelay=(SeekBar) findViewById(R.id.seekBarDelay);
    	 valueDelay=(TextView) findViewById(R.id.textViewDelayvalue);
    	 seekbardelay.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				valueDelay.setText(" value is "+progress);
			}
		});
    }
    
    public void connect()
	{
		if (m_isConnected)
		{
			Log.d(TAG,"Bluetooth is already connected");
			return;
		}		
		
		if(m_ConTask == null || m_ConTask.getStatus() != AsyncTask.Status.RUNNING) {
			if(m_ConTask != null) {
				m_ConTask.cancel(true);
			}
			m_ConTask = new ConnectTask();
			m_ConTask.execute((Void)null);
		}
	}
    
    public void checkButtons() {
		if(m_isConnected) {
			for(int i = 0; i < radioModeGroup.getChildCount(); i++){
			    ((RadioButton)radioModeGroup.getChildAt(i)).setEnabled(true);
			}
		}
		else {
			for(int i = 0; i < radioModeGroup.getChildCount(); i++){
			    ((RadioButton)radioModeGroup.getChildAt(i)).setEnabled(false);
			}
		}
	}
    
    private class ConnectTask extends AsyncTask<Void,String,Void> {
		ProgressDialog mDialog;
		
		protected void onPreExecute() {
			mDialog = ProgressDialog.show(MainActivity.this,null,"Connecting to Cannon",true,true);
		}
		protected Void doInBackground(Void... arg0) {
			Log.d(TAG,"ConnectTask started");
			if (m_isConnected)
			{
				return null;
			}
			
			try
			{		    	
		        Method m = m_Cannon.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
		        m_BtSocket = (BluetoothSocket) m.invoke(m_Cannon, Integer.valueOf(1));
			    Log.d(TAG, "Bluetooth is connecting...");
			        
			    m_BtSocket.connect();
	            publishProgress("The Bluetooth connection is good!");
			    m_isConnected = true;
			    return null;
			}
			catch (Exception e)
			{
	            publishProgress("The Bluetooth connection failed.");
				m_isConnected = false;
				Log.e(TAG, e.toString());
				return null;
			}
		}
		
		protected void onProgressUpdate(String...values)
		{
			Toast.makeText(MainActivity.this, values[0], Toast.LENGTH_LONG).show();
		}
		
		protected void onPostExecute(Void Result)
		{
			mDialog.cancel();
			checkButtons();
			Log.d(TAG,"ConnectTask finished");
		}
	}
    
    public void fire(int barrel)
	{
        if(m_FireTask == null || m_FireTask.getStatus() != AsyncTask.Status.RUNNING)
        	if(m_FireTask != null) {
        		m_FireTask.cancel(true);
        	}
        	m_FireTask = new FireTask();
        	m_FireTask.execute(barrel);
    }
    
    private class FireTask extends AsyncTask<Integer,String,Void> {
		protected Void doInBackground(Integer...args) {
			Log.d(TAG,"FireTask started for barrel " + args[0]);
			
	        try
	        {	        	
	        	if(!m_isConnected) {
	        		publishProgress("Not connected. Cannot fire.");
					Log.e(TAG, "Tried to fire without being connected");
					return null;
	        	}
	        	
			    Log.d(TAG, "Bluetooth is connected!");
			        
			    m_Output = m_BtSocket.getOutputStream();
			    char cmd = 'm';
			    m_Output.write(cmd);
			    Log.d(TAG,"BT write successful. CMD=" + cmd);
			    return null;
	        }
	        catch (Exception e)
	        {
	        	publishProgress("The fire command could not be sent. Try reconnecting.");
	        	Log.e(TAG, e.toString());
	        	Log.d(TAG,"Returning to unconnected state");
	        	m_isConnected = false;
	        	checkButtons();
	        	return null;
	        }			
		}
		
		protected void onProgressUpdate(String...strings) {
			Toast.makeText(MainActivity.this,strings[0],Toast.LENGTH_LONG).show();
		}
		
		protected void onPostExecute(Void result) {
			Log.d(TAG,"FireTask finished");
		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
