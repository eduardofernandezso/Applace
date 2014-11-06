package com.retni.applacegps;

import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class Compartir extends FragmentActivity {

	private LoginButton loginBtn;
	private Button postImageBtn;
	ProgressBar post_bar;
	private Button irmapa;

	private TextView userName;

	private UiLifecycleHelper uiHelper;

	private static final List<String> PERMISSIONS = Arrays.asList("public_profile","email","user_likes","publish_actions");

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
		
		setContentView(R.layout.compartirfb);
		Parse.initialize(this, "XyEh8xZwVO3Fq0hVXyalbQ0CF81zhcLqa0nOUDY3", "bK1hjOovj0GAmgIsH6DouyiWOHGzeVz9RxYc6vur");
		ParseFacebookUtils.initialize(getString(R.string.app_id));
		
		post_bar = (ProgressBar) findViewById(R.id.progressBar1);
		userName = (TextView) findViewById(R.id.user_name);
		irmapa = (Button) findViewById(R.id.irmapa);
		irmapa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				iraActivity();
			}
		});
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					userName.setText("Bienvenido, " + "\n" +user.getName());
				} else {
					userName.setText("Inicie sesión en Facebook para compartir");
				}
			}
		});
		//ParseUser currentUser = ParseUser.getCurrentUser();	
		
		postImageBtn = (Button) findViewById(R.id.post_image);
		postImageBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				postImage();
			}
		});

		ParseUser currentUser = ParseUser.getCurrentUser();
		if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
			// Go to the user info activity
			//iraActivity();
		}
		buttonsEnabled(false);
	}
	
	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				buttonsEnabled(true);
				Log.d("FacebookSampleActivity", "Facebook session opened");
				postImageBtn.setVisibility(View.VISIBLE);
				irmapa.setVisibility(View.VISIBLE);
			} else if (state.isClosed()) {
				
				AlertDialog.Builder dialog = new AlertDialog.Builder(Compartir.this);  
    	        dialog.setTitle("Cerrar Sesión");		
    	        dialog.setIcon(R.drawable.ic_launcher);	
    	        
    	        View v = getLayoutInflater().inflate( R.layout.dialog, null );
    			      
    	        dialog.setView(v);
    	        dialog.setNegativeButton("Cancelar", null);  
    	        dialog.setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {  
    	            public void onClick(DialogInterface dialogo1, int id) {
    	            	ParseUser.logOut();
    	             	Intent i = new Intent(Compartir.this, MainActivity.class );
						startActivity(i);
    	            }  
    	        });  
    	        
    	        dialog.show();
				
				
				buttonsEnabled(false);
				Log.d("FacebookSampleActivity", "Facebook session closed");
				postImageBtn.setVisibility(View.INVISIBLE);
			}
		}
	};

	public void buttonsEnabled(boolean isEnabled) {
		postImageBtn.setEnabled(isEnabled);
	}

	
	/*------------------------------------------------------------------------------------------*/
	
	public void postImage() {
		post_bar.setVisibility(View.VISIBLE);
		if (checkPermissions()) {
			Bitmap img = BitmapFactory.decodeResource(getResources(),
					R.drawable.shareapplace);
			Request uploadRequest = Request.newUploadPhotoRequest(
					Session.getActiveSession(), img, new Request.Callback() {
						@Override
						public void onCompleted(Response response) {
							Toast.makeText(Compartir.this,
									"Foto subida con exito",
									Toast.LENGTH_LONG).show();
							post_bar.setVisibility(View.INVISIBLE);
						}
					});
			uploadRequest.executeAsync();
		} else {
			requestPermissions();
		}
		
	}

	/*------------------------------------------------------------------------------------------*/

	public boolean checkPermissions() {
		Session s = Session.getActiveSession();
		if (s != null) {
			return s.getPermissions().contains("publish_actions");
		} else
			return false;
	}

	public void requestPermissions() {
		Session s = Session.getActiveSession();
		if (s != null)
			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
					this, PERMISSIONS));
	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
		buttonsEnabled(Session.getActiveSession().isOpened());
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

	private void iraActivity() {
		//ParseUser user = ParseUser.getCurrentUser();
		//user.setUsername();
		Intent intent = new Intent(Compartir.this, Logueado.class);
		startActivity(intent);
	}
}