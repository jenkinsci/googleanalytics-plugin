package hudson.plugins.google.analytics;

import hudson.Plugin;
import hudson.model.PageDecorator;

public class PluginImpl extends Plugin {

    private GoogleAnalyticsPageDecorator pageDecorator;

    @Override
    public void start() throws Exception {
        pageDecorator = new GoogleAnalyticsPageDecorator();
        PageDecorator.ALL.add(pageDecorator);
        super.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        PageDecorator.ALL.remove(pageDecorator);
    }
}
