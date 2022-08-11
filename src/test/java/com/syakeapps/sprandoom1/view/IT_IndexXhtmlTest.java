package com.syakeapps.sprandoom1.view;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syakeapps.sprandoom1.page.IndexXhtmlPage;

@RunAsClient
public class IT_IndexXhtmlTest extends Arquillian {
    private static final String RESOURCES_ROOT = "src/main/resources";
    private static final String WEBAPP_ROOT = "src/main/webapp";
    private static final String INDEX_URL = "http://127.0.0.1:8080/sprandoom1/index.faces";

    @Deployment
    public static WebArchive createDeployment() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml").importDependencies(ScopeType.COMPILE).resolve()
                .withTransitivity().asFile();

        return ShrinkWrap.create(WebArchive.class).addPackages(true, "com.syakeapps")
                .addAsResource(new File(RESOURCES_ROOT + "/locale", "Messages_en.properties"),
                        "locale/Messages_en.properties")
                .addAsResource(new File(RESOURCES_ROOT + "/locale", "Messages_ja.properties"),
                        "locale/Messages_ja.properties")
                .addAsResource(new File(RESOURCES_ROOT + "/locale", "Messages.properties"),
                        "locale/Messages.properties")
                .addAsResource(new File(RESOURCES_ROOT + "/META-INF", "persistence.xml"), "META-INF/persistence.xml")
                .addAsResource(new File(RESOURCES_ROOT + "/sql", "create.sql"), "sql/create.sql")
                .addAsResource(new File(RESOURCES_ROOT + "/sql", "insert.sql"), "sql/insert.sql")
                .addAsWebInfResource(new File(WEBAPP_ROOT + "/WEB-INF", "beans.xml"))
                .addAsWebInfResource(new File(WEBAPP_ROOT + "/WEB-INF", "faces-config.xml"))
                .addAsWebInfResource(new File(WEBAPP_ROOT + "/WEB-INF", "jboss-web.xml"))
                .setWebXML(new File(WEBAPP_ROOT + "/WEB-INF", "web.xml"))
                .addAsWebResource(new File(WEBAPP_ROOT, "app.webmanifest"))
                .addAsWebResource(new File(WEBAPP_ROOT, "error500.html"))
                .addAsWebResource(new File(WEBAPP_ROOT, "index.xhtml"))
                .addAsWebResource(new File(WEBAPP_ROOT, "service-worker.js"))
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                        .importDirectory(new File(WEBAPP_ROOT + "/css")).as(GenericArchive.class), "/css")
                .merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                        .importDirectory(new File(WEBAPP_ROOT + "/img")).as(GenericArchive.class), "/img")
                .addAsLibraries(libs);
    }

    @SuppressWarnings("serial")
    @DataProvider(name = "params")
    public Object[][] params() {
        List<Locale> locales = new ArrayList<>() {
            {
                add(Locale.ENGLISH);
                add(Locale.JAPANESE);
                add(Locale.FRENCH);
            }
        };

        List<String> options = new ArrayList<>() {
            {
                add("--headless");
                add("--disable-gpu");
                add("--window-size=960,1080");
                add("--disk-cache-size=0");
            }
        };

        Object[][] params = new Object[locales.size()][];
        for (int i = 0; i < locales.size(); i++) {
            Locale locale = locales.get(i);

            options.add("--lang=" + locale.getLanguage());
            WebDriver driver = new ChromeDriver(new ChromeOptions().addArguments(options));

            ResourceBundle.Control control = ResourceBundle.Control
                    .getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);
            ResourceBundle bundle = ResourceBundle.getBundle("locale.Messages", locale, control);

            params[i] = new Object[] { locale, driver, bundle };
        }

        return params;
    }

    @Test(dataProvider = "params")
    public void runIntegrationTest(Locale locale, WebDriver driver, ResourceBundle bundle) {
        try {
            /* SETUP */
            driver.get(INDEX_URL);
            IndexXhtmlPage target = new IndexXhtmlPage(driver);

            /* EXECUTION */
            // first view assertion
            assertEquals(target.getMetaDescriptionContent(), bundle.getString("META_DESCRIPTION"));
            assertEquals(target.getPageTitle(), bundle.getString("META_TITLE"));
            assertEquals(target.getHowToUseLinkText(), bundle.getString("LABEL_WHATSTHIS"));
            assertEquals(target.getSettingPanelAreaTitleText(), bundle.getString("LABEL_SETTING"));

            target = target.openHowToUseModal();

            // HowToUse modal view assertion
            assertTrue(target.isVisibleHowToUseModal());
            assertEquals(target.getHowToUseModalTitleText(), bundle.getString("LABEL_WHATSTHIS"));
            assertEquals(target.getHowToUseText(), bundle.getString("TEXT_HOWTOUSE"));
            assertEquals(target.getHowToUseCautionText(), bundle.getString("TEXT_CAUTION") + "(@syakeApps)");
            assertEquals(target.getHowToUseModalCloseButtonText(), bundle.getString("LABEL_CLOSE"));

            target = target.closeHowToUseModal();

            // HowToUse modal invisible assertion
            assertFalse(target.isVisibleHowToUseModal());

            target = target.clickSettingPanel();

            // setting panel opened assertion
            assertTrue(target.isOpenedSettingPanel());
            assertEquals(target.getWeaponClassLabelText(), bundle.getString("LABEL_CLASS"));
            assertEquals(target.getSubLabelText(), bundle.getString("LABLE_SUB"));
            assertEquals(target.getSpecialLabelText(), bundle.getString("LABLE_SPECIAL"));

            target = target.clickSettingPanel();

            // setting panel closed assertion
            assertFalse(target.isOpenedSettingPanel());

            target = target.clickRandomizeButton();

            // randomized view assertion
            assertEquals(target.getMetaDescriptionContent(), bundle.getString("META_DESCRIPTION"));
            assertEquals(target.getPageTitle(), bundle.getString("META_TITLE"));
            assertEquals(target.getHowToUseLinkText(), bundle.getString("LABEL_WHATSTHIS"));
            assertEquals(target.getSettingPanelAreaTitleText(), bundle.getString("LABEL_SETTING"));

        } catch (Exception e) {
            fail("Exception occured.", e);

        } finally {
            if (driver != null) {
                driver.manage().deleteAllCookies();
                driver.close();
                driver.quit();
            }
        }
    }
}
