package hudson.plugins.google.analytics;

import hudson.model.PageDecorator;
import org.jvnet.hudson.test.HudsonTestCase;
import org.jvnet.hudson.test.recipes.LocalData;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FooterWebTest extends HudsonTestCase {

    /**
     * Asserts that the footer contains the profile within quotes.
     */
    @LocalData
    public void testFooterContainsProfileWithinQuotation() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.profileId", "AProfileId");
        assertTrue("The page text did not contain the profile", 
                page.asXml().contains("_gat._getTracker(\"AProfileId\")"));
    }
    /**
     * Asserts that the footer contains the profile within quotes.
     */
    @LocalData
    public void testFooterContainsProfileWithDomainName() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.domainName", "ADomain");
        assertTrue("The page text did not contain the profile", 
                page.asXml().contains("pageTracker._setDomainName(\"ADomain\")"));
    }
    /**
     * Asserts that the footer does not contain the google analytics script.
     */
    public void testEmptyFooterIfEmptyProfileId() throws Exception {
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
    @SuppressWarnings("deprecation")
    public void testSubmittingConfigurationUpdatesProfileId() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setThrowExceptionOnScriptError(false);
        HtmlForm form = webClient.goTo("configure").getFormByName("config");
        form.getInputByName("_.profileId").setValueAttribute("NewProfile");
        form.submit((HtmlButton)last(form.getHtmlElementsByTagName("button")));
        assertEquals("The new profile id wasnt correct", "NewProfile", ((GoogleAnalyticsPageDecorator) PageDecorator.all().get(0)).getProfileId());
    }    
}
