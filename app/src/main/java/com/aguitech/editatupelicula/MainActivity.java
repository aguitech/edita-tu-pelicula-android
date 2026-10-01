package com.aguitech.editatupelicula;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.GeolocationPermissions;
import android.webkit.CookieManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends Activity {

    // URL principal del WebView
    private static final String HOME_URL = "https://tupeliculafinanciera.com/edita-tu-pelicula/";

    // Permisos que necesitamos pedir al usuario
    private static final String[] REQUIRED_PERMISSIONS = new String[]{
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO
    };

    private static final int PERMISSION_REQUEST_CODE = 1001;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fullscreen / immersive
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        getWindow().setStatusBarColor(0xFF050608);

        // Crear WebView programáticamente
        webView = new WebView(this);
        setContentView(webView);

        // Configuración del WebView
        setupWebView();

        // Cargar URL inicial
        loadHome();

        // Pedir permisos (no bloquea, en background)
        requestRequiredPermissions();
    }

    /**
     * Configuración completa del WebView optimizada para edición de video
     */
    private void setupWebView() {
        WebSettings settings = webView.getSettings();

        // JavaScript habilitado (necesario para el editor de video)
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        // DOM storage
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);

        // Cache
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        // Soporte para archivos (uploads)
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        // Viewport responsive
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);

        // Texto
        settings.setTextZoom(100);

        // Mixed content (por si el sitio tiene http embebido)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }

        // User agent moderno (identifica como app móvil)
        String userAgent = settings.getUserAgentString();
        settings.setUserAgentString(userAgent + " EditaTuPeliculaApp/1.0 (Android)");

        // Cookies
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setAcceptThirdPartyCookies(webView, true);

        // Hardware acceleration (importante para video)
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        // WebChromeClient para manejar permisos (cámara, mic, etc)
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                // Aprobar automáticamente los recursos solicitados
                runOnUiThread(() -> {
                    String[] resources = request.getResources();
                    for (String resource : resources) {
                        if (resource.equals(PermissionRequest.RESOURCE_VIDEO_CAPTURE) ||
                            resource.equals(PermissionRequest.RESOURCE_AUDIO_CAPTURE)) {
                            request.grant(resources);
                            return;
                        }
                    }
                    // Si no es cámara/mic, denegar
                    request.deny();
                });
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        // WebViewClient para forzar que TODO se abra dentro de la app
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                // Mantener navegación dentro del dominio principal
                if (url.contains("tupeliculafinanciera.com") ||
                    url.startsWith("http://") || url.startsWith("https://")) {
                    return false; // carga normal
                }
                // Para deep links externos, abrir en el navegador
                if (url.startsWith("mailto:") || url.startsWith("tel:")) {
                    return false;
                }
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    /**
     * Cargar URL principal
     */
    private void loadHome() {
        webView.loadUrl(HOME_URL);
    }

    /**
     * Pedir permisos runtime (API 23+)
     */
    private void requestRequiredPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            java.util.List<String> permissionsNeeded = new java.util.ArrayList<>();

            for (String permission : REQUIRED_PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                    permissionsNeeded.add(permission);
                }
            }

            if (!permissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    permissionsNeeded.toArray(new String[0]),
                    PERMISSION_REQUEST_CODE
                );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // Permisos otorgados o denegados — el WebView manejará lo que necesite
        }
    }

    /**
     * Back button → navegar al historial o salir
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pausar cualquier video cuando la app va a background
        webView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }
}