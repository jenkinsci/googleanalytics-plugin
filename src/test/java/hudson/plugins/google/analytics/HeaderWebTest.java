package hudson.plugins.google.analytics;

import hudson.model.PageDecorator;
import org.jvnet.hudson.test.HudsonTestCase;
import org.jvnet.hudson.test.recipes.LocalData;

import com.gargoylesoftware.htmlunit.WebAssert;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHead;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HeaderWebTest extends HudsonTestCase {

    /**
     * Asserts that the analytics script is in the head element.
     */
    @LocalData
    public void testHeadElementContainsScript() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.profileId", "AProfileId");
        HtmlHead item = (HtmlHead) page.getElementsByTagName(HtmlHead.TAG_NAME).item(0);
        assertTrue("The page text did not contain the google analytics script", 
                item.asXml().contains("_gaq.push(['_setAccount', 'AProfileId']);"));
    }
    
    /**
     * Asserts that the header contains the profile within quotes.
     */
    @LocalData
    public void testScriptContainsProfileWithinQuotation() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.profileId", "AProfileId");
        assertTrue("The page text did not contain the profile", 
                page.asXml().contains("_gaq.push(['_setAccount', 'AProfileId']);"));
    }
    /**
     * Asserts that page contains the profile within quotes.
     */
    @LocalData
    public void testScriptForDomain() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.domainName", "ADomain");
        assertTrue("The page text did not contain the _setDomainName value", 
                page.asXml().contains("_gaq.push(['_setDomainName', 'ADomain']);"));
    }
    /**
     * Asserts that page contains the profile within quotes.
     */
    @LocalData
    public void testScriptForMultipleTopLevelDomains() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.domainName", "none");
        assertTrue("The page text did not contain the none Domain name", 
                page.asXml().contains("_gaq.push(['_setDomainName', 'none']);"));
        assertTrue("The page text did not contain the _setAllowLinker value", 
                page.asXml().contains("_gaq.push(['_setAllowLinker', true]);"));
    }
    /**
     * Asserts that the header does not contain the google analytics script.
     */
    public void testEmptyHeaderIfEmptyProfileId() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setJavaScriptEnabled(false);
        HtmlPage page = webClient.goTo("configure");
        WebAssert.assertInputContainsValue(page, "_.profileId", "");
        assertFalse("The page text contained the profile", 
                page.asXml().contains("_gaq.push(['_setAccount', 'AProfileId']);"));
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
