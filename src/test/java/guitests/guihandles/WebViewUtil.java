package guitests.guihandles;

import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.MalformedURLException;
import java.net.URL;

import guitests.GuiRobot;

import javafx.scene.web.WebView;

import seedu.address.MainApp;

/**
 * Helper methods for dealing with {@code WebView}.
 */
public class WebViewUtil {

    /**
     * Returns the {@code URL} of the currently loaded page in the {@code webView}.
     */
    public static URL getLoadedUrl(WebView webView) {
        try {
            return new URL(webView.getEngine().getLocation());
        } catch (MalformedURLException mue) {
            return MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
            //throw new AssertionError("webView should not be displaying an invalid URL.", mue);
        }

    }

    /**
     * If the {@code browserPanelHandle}'s {@code WebView} is loading, sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilBrowserLoaded(BrowserPanelHandle browserPanelHandle) {
        new GuiRobot().waitForEvent(browserPanelHandle::isLoaded);
    }
}
