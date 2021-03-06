package kr.co.chunhoshop;

import kr.co.chunhoshop.util.ParserTag;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TwitterLogin extends Activity implements ParserTag {
	// INTENT
	Intent mIntent;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_login);
		WebView webView = (WebView) findViewById(R.id.webView);

		// 화면 전환시 WebView에서 화면 전환하도록한다.
		// 이렇게하지 않으면 표준 브라우저가 열려 버린다.
		webView.setWebViewClient(new WebViewClient() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * android.webkit.WebViewClient#onPageStarted(android.webkit.WebView
			 * , java.lang.String, android.graphics.Bitmap)
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				// 로그아웃을 처리한후에는 바로 Activity를 종료시킨다.
				// if (url != null && url.equals("http://mobile.twitter.com/"))
				// {
				// finish();
				// }
				// 로그인이 성공하면
				// else
				if (url != null && url.startsWith(TWITTER_CALLBACK_URL)) {
					String[] urlParameters = url.split("\\?")[1].split("&");
					String oauthToken = "";
					String oauthVerifier = "";

					try {
						if (urlParameters[0].startsWith("oauth_token")) {
							oauthToken = urlParameters[0].split("=")[1];
						} else if (urlParameters[1].startsWith("oauth_token")) {
							oauthToken = urlParameters[1].split("=")[1];
						}

						if (urlParameters[0].startsWith("oauth_verifier")) {
							oauthVerifier = urlParameters[0].split("=")[1];
						} else if (urlParameters[1]
								.startsWith("oauth_verifier")) {
							oauthVerifier = urlParameters[1].split("=")[1];
						}

						mIntent.putExtra("oauth_token", oauthToken);
						mIntent.putExtra("oauth_verifier", oauthVerifier);

						setResult(RESULT_OK, mIntent);
						finish();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			public void onPageFinished(WebView view, String url) {
				// page 렌더링이 완료되면 호출됨.
				super.onPageFinished(view, url);
			}
		});

		mIntent = getIntent();
		String url1 = mIntent.getStringExtra("auth_url");
		webView.loadUrl(url1);
	}
}