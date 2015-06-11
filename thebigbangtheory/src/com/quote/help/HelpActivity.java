package com.quote.help;

import java.io.IOException;
import java.io.InputStream;

import com.tbbtquotes.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class HelpActivity extends Activity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		webView = (WebView) findViewById(R.id.webviewhelp);
		webView.getSettings().setJavaScriptEnabled(true);
		// webView.loadUrl("http://www.google.com");

		String customHtml = null;
		InputStream stream = getResources()
				.openRawResource(R.raw.abouthelpview);
		try {
			byte[] buffer = new byte[stream.available()];
			stream.read(buffer);
			stream.close();
			customHtml = new String(buffer);
		} catch (IOException e) {
			// Error handling
		}
		webView.loadDataWithBaseURL("file:///android_asset/", customHtml,
				"text/html", "utf-8", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the drawer is open, hide action items related to the content view
		menu.findItem(R.id.action_settings).setVisible(false);
		return super.onPrepareOptionsMenu(menu);
	}
}
