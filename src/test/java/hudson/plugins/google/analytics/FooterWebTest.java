package hudson.plugins.google.analytics;

import hudson.model.PageDecorator;
import org.jvnet.hudson.test.HudsonTestCase;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FooterWebTest extends HudsonTestCase {

    private GoogleAnalyticsPageDecorator pageDecorator;
    
    @Override
    protected void tearDown() throws Exception {
        if (pageDecorator != null) {
            PageDecorator.ALL.remove(pageDecorator);
        }
        super.tearDown();
    }

    /**
     * Asserts that the footer contains the profile within quotes.
     */
    public void testFooterContainsProfileWithinQuotation() throws Exception {
        pageDecorator = new GoogleAnalyticsPageDecorator("AProfileId");
        PageDecorator.ALL.add(pageDecorator);
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.profileId", "AProfileId");
        assertTrue("The page text did not contain the profile", 
                page.asXml().contains("_gat._getTracker(\"AProfileId\")"));
    }
    /**
     * Asserts that the footer does not contain the google analytics script.
     */
    public void testEmptyFooterIfEmptyProfileId() throws Exception {
        pageDecorator = new GoogleAnalyticsPageDecorator("");
        PageDecorator.ALL.add(pageDecorator);
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.profileId", "");
        assertFalse("The page text contained the profile", 
                page.asXml().contains("_gat._getTracker("));
    }

    /**
     * Asserts that the profile id for decorator is updated when submitted
     */
    public void testSubmittingConfigurationUpdatesProfileId() throws Exception {
        pageDecorator = new GoogleAnalyticsChild("111");
        PageDecorator.ALL.add(pageDecorator);
        WebClient webClient = new WebClient();
        HtmlForm form = webClient.goTo("configure").getFormByName("config");
        form.getInputByName("_.profileId").setValueAttribute("NewProfile");
        form.submit((HtmlButton)last(form.getHtmlElementsByTagName("button")));
        assertEquals("The new profile id wasnt correct", "NewProfile", pageDecorator.getProfileId());
    }
    
    // Class that has a different footer.jelly file than the real page decorator
    // to ignore the javascript errors.
    private static class GoogleAnalyticsChild extends GoogleAnalyticsPageDecorator {
        public GoogleAnalyticsChild(String string) {
            super(string);
        }
    }
}
