package com.syakeapps.sprandoom1.view;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.syakeapps.sprandoom1.page.IndexXhtmlPage;

@RunAsClient
public class IndexXhtmlTest extends Arquillian {
    private static final String RESOURCES_ROOT = "src/main/resources";
    private static final String WEBAPP_ROOT = "src/main/webapp";
    private static final String INDEX_URL = "http://127.0.0.1:8081/sprandoom1/index.faces";

    private static List<Locale> locales = Arrays.asList(Locale.ENGLISH, Locale.JAPANESE, Locale.FRENCH);
    private static Map<Locale, WebDriver> drivers = new HashMap<>();
    private static IndexXhtmlPage target;

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
                .addAsResource(new File(RESOURCES_ROOT + "/META-INF/sql", "create.sql"), "META-INF/sql/create.sql")
                .addAsResource(new File(RESOURCES_ROOT + "/META-INF/sql", "insert.sql"), "META-INF/sql/insert.sql")
                .addAsWebInfResource(new File(WEBAPP_ROOT + "/WEB-INF", "beans.xml"))
                .addAsWebInfResource(new File(WEBAPP_ROOT + "/WEB-INF", "faces-config.xml"))
                .addAsWebInfResource(new File(WEBAPP_ROOT + "/WEB-INF", "jboss-web.xml"))
                .addAsWebInfResource(new File(WEBAPP_ROOT + "/WEB-INF", "web.xml"))
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

    @DataProvider(name = "locales")
    public Object[] locales() {
        return locales.toArray();
    }

    @BeforeClass
    public static void setup() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:/webdriver/chrome/104.0.5112.79/chromedriver.exe");
        locales.forEach(l -> {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080", "--disk-cache-size=0",
                    "--lang=" + l.getLanguage());
            WebDriver driver = new ChromeDriver(options);
            drivers.put(l, driver);
        });
    }

    @BeforeMethod
    public void before() {
        drivers.values().forEach(d -> {
            d.get(INDEX_URL);
        });
    }

    @AfterClass
    public static void shutdown() {
        drivers.values().forEach(d -> {
            if (d != null) {
                d.close();
            }
        });
    }

    @Test(dataProvider = "locales")
    public void testFirstView(Locale locale) {
        /* SETUP */
        ResourceBundle.Control control = ResourceBundle.Control
                .getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);
        ResourceBundle bundle = ResourceBundle.getBundle("locale.Messages", locale, control);

        /* EXECUTION */
        target = new IndexXhtmlPage(drivers.get(locale));

        /* ASSERTION */
        assertEquals(target.getMetaDescriptionContent(), bundle.getString("META_DESCRIPTION"));
        assertEquals(target.getPageTitle(), bundle.getString("META_TITLE"));
        assertEquals(target.getHowToUseLinkText(), bundle.getString("LABEL_WHATSTHIS"));
        assertEquals(target.getSettingPanelAreaTitleText(), bundle.getString("LABEL_SETTING"));
    }

    @Test(dataProvider = "locales")
    public void testVisibleHowToUseModalView(Locale locale) {
        /* SETUP */
        ResourceBundle.Control control = ResourceBundle.Control
                .getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);
        ResourceBundle bundle = ResourceBundle.getBundle("locale.Messages", locale, control);

        /* EXECUTION */
        target = new IndexXhtmlPage(drivers.get(locale)).openHowToUseLink();

        /* ASSERTION */
        assertEquals(target.getHowToUseModalTitleText(), bundle.getString("LABEL_WHATSTHIS"));
        assertEquals(target.getHowToUseText(), bundle.getString("TEXT_HOWTOUSE"));
        assertEquals(target.getHowToUseCautionText(), bundle.getString("TEXT_CAUTION") + "(@syakeApps)");
        assertEquals(target.getHowToUseModalCloseButtonText(), bundle.getString("LABEL_CLOSE"));
    }

    @Test(dataProvider = "locales")
    public void testVisibleSettingView(Locale locale) {
        /* SETUP */
        ResourceBundle.Control control = ResourceBundle.Control
                .getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);
        ResourceBundle bundle = ResourceBundle.getBundle("locale.Messages", locale, control);

        /* EXECUTION */
        target = new IndexXhtmlPage(drivers.get(locale)).openSettingPanel();

        /* ASSERTION */
        assertEquals(target.getWeaponClassLabelText(), bundle.getString("LABEL_CLASS"));
        assertEquals(target.getSubLabelText(), bundle.getString("LABLE_SUB"));
        assertEquals(target.getSpecialLabelText(), bundle.getString("LABLE_SPECIAL"));
    }

    @Test(dataProvider = "locales")
    public void testRandomizedView(Locale locale) {
        /* SETUP */
        ResourceBundle.Control control = ResourceBundle.Control
                .getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT);
        ResourceBundle bundle = ResourceBundle.getBundle("locale.Messages", locale, control);

        /* EXECUTION */
        target = new IndexXhtmlPage(drivers.get(locale)).randomize();

        /* ASSERTION */
        assertEquals(target.getMetaDescriptionContent(), bundle.getString("META_DESCRIPTION"));
        assertEquals(target.getPageTitle(), bundle.getString("META_TITLE"));
        assertEquals(target.getHowToUseLinkText(), bundle.getString("LABEL_WHATSTHIS"));
        assertEquals(target.getSettingPanelAreaTitleText(), bundle.getString("LABEL_SETTING"));
    }
}
