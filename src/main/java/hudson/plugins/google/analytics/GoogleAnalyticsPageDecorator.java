package hudson.plugins.google.analytics;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import hudson.Extension;
import hudson.Util;
import hudson.model.PageDecorator;

@Extension
public class GoogleAnalyticsPageDecorator extends PageDecorator {

    private String profileId;
    private String domainName;

    @DataBoundConstructor
    public GoogleAnalyticsPageDecorator(String profileId, String domainName) {
        this();
        this.profileId = profileId;
        this.domainName = domainName;
    }
    
    public GoogleAnalyticsPageDecorator() {
        super(GoogleAnalyticsPageDecorator.class);
        load();
    }

    @Override
    public String getDisplayName() {
        return "Google Analytics";
    }

    @Override
    public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
        req.bindJSON(this, json);
        save();
        return true;
    }

    public String getProfileId() {
        return Util.fixEmpty(profileId);
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
    
    public String getDomainName() {
        return Util.fixEmpty(domainName);
    }
    
    public void setDomainName(String domainName) {
      this.domainName = domainName;
    }
}
