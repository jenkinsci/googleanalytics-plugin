package hudson.plugins.google.analytics;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

import hudson.Util;
import hudson.model.PageDecorator;

public class GoogleAnalyticsPageDecorator extends PageDecorator {

    private String profileId;

    public GoogleAnalyticsPageDecorator(String profileId) {
        this();
        this.profileId = profileId;
    }
    
    protected GoogleAnalyticsPageDecorator() {
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
}
