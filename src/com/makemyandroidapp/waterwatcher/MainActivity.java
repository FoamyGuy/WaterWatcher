package com.makemyandroidapp.waterwatcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {

	SharedPreferences prefs;
	SharedPreferences.Editor prefsEdit;
	private int TARGET_OUNCES = 64;
	
	private int ozInBottle;
	private int count;
	private Button countBtn;
	private TextView countTxt;
	private ProgressBar dayProgress;
	
	private CharSequence[] bottleSizes = {"8oz", "12oz", "16oz", "20oz", "24oz", "32oz"};
	private CharSequence[] options = {"Clear", "Set Container Size"};
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = this.getSharedPreferences("Default", Context.MODE_PRIVATE);
        prefsEdit = prefs.edit();
        count = prefs.getInt("count", 0);
        ozInBottle = prefs.getInt("oz", 0);
        countBtn = (Button)findViewById(R.id.countBtn);
        countTxt = (TextView)findViewById(R.id.countTxt);
        dayProgress = (ProgressBar)findViewById(R.id.countPrg);
        
        dayProgress.setMax(TARGET_OUNCES);
        
        
        updateOutput();
        countBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v){
        		count++;
        		updateOutput();
        		
        		if((count * ozInBottle) >= TARGET_OUNCES){
        			showCongratsDialog();
        		}
        	}
        });
        
        countBtn.setOnLongClickListener(new OnLongClickListener() {
        	public boolean onLongClick(View v){
        		showOptionsDialog();
        		
        		return true;
        	}
        });
        
        
        Log.i("WaterWatcher", "ozInBottle = " + ozInBottle);
        if(ozInBottle == 0){
        	showBottleSizeDialog();
        }
        
        
        
    }
    
    public void showCongratsDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Congradulations!")
    	.setCancelable(true)
    	.setMessage("You made it to 64oz today, Good Job!!")
    	.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				
			}
		});
    		

    		

    	AlertDialog ozDialog = builder.create();		
    	ozDialog.show();
    }
    
    
    public void showBottleSizeDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Options")
    	
    	.setItems(bottleSizes, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				case 0:
					ozInBottle = 8;
				break;
				case 1:
					ozInBottle = 12;
				break;
				case 2:
					ozInBottle = 16;
				break;
				case 3:
					ozInBottle = 20;
				break;
				case 4:
					ozInBottle = 24;
				break;
				case 5:
					ozInBottle = 32;
				break;
				
				}
				
				prefsEdit.putInt("oz", ozInBottle);
			}
    		
    	})
    	
    	.setCancelable(false);
    	AlertDialog ozDialog = builder.create();		
    	ozDialog.show();
    }
    
    public void showOptionsDialog(){
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Options")
    	.setCancelable(true)
    	.setItems(options, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				case 0:
					count = 0;
					updateOutput();
				break;
				case 1:
					showBottleSizeDialog();
				break;
								
				}
				
				prefsEdit.putInt("oz", ozInBottle);
			}
    		
    	})
    	
    	.setCancelable(false);
    	AlertDialog ozDialog = builder.create();		
    	ozDialog.show();
    }
    
    public void updateOutput(){
    	StringBuilder sb = new StringBuilder("Containers: ");
    	sb.append(count);
    	sb.append("\n");
    	sb.append("Ounces: ");
    	sb.append(count * ozInBottle);
    	sb.append("/64");
    	countTxt.setText(sb.toString());
    	dayProgress.setProgress(count * ozInBottle);
    	
    }
    
    
    public void onStop() {
    	super.onStop();
    	
    	prefsEdit.putInt("count", count);
    	prefsEdit.commit();
    	
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }*/
    
}
