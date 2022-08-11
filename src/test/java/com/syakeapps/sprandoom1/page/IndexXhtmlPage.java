package com.syakeapps.sprandoom1.page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IndexXhtmlPage {
    private WebDriver driver;

    /* HEAD */
    @FindBy(xpath = "//meta[@name='description']")
    private WebElement metaDescription;

    /* BODY */
    @FindBy(id = "howtouse-modal")
    private WebElement howToUseModal;

    @FindBy(id = "banner-area")
    private WebElement bannerArea;

    @FindBy(id = "howtouse-link-area")
    private WebElement howToUseLinkArea;

    @FindBy(id = "weapon-img-area")
    private WebElement weaponImgArea;

    @FindBy(id = "weapon-name-area")
    private WebElement weaponNameArea;

    @FindBy(id = "setting-panel-area")
    private WebElement settingPanelArea;

    @FindBy(id = "randomize-btn-area")
    private WebElement randomizeBtnArea;

    public IndexXhtmlPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("randomize-btn-area")));
        this.driver = driver;
    }

    /* GETTERS */
    public String getMetaDescriptionContent() {
        return metaDescription.getAttribute("content");
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getHowToUseModalTitleText() {
        return howToUseModal.findElement(By.xpath(".//h4")).getText();
    }

    public String getHowToUseText() {
        return howToUseModal.findElement(By.xpath(".//p[1]")).getText();
    }

    public String getHowToUseCautionText() {
        return howToUseModal.findElement(By.xpath(".//p[2]")).getText();
    }

    public String getHowToUseModalCloseButtonText() {
        return howToUseModal.findElement(By.xpath(".//button[@type='button' and @class='btn btn-secondary']"))
                .getText();
    }

    public String getHowToUseLinkText() {
        return howToUseLinkArea.findElement(By.xpath(".//button[@type='button']")).getText();
    }

    public String getSettingPanelAreaTitleText() {
        return settingPanelArea.findElement(By.xpath(".//h4/a")).getText();
    }

    public String getWeaponClassLabelText() {
        return settingPanelArea.findElement(By.xpath(".//label[@for='class']")).getText();
    }

    public String getSubLabelText() {
        return settingPanelArea.findElement(By.xpath(".//label[@for='sub']")).getText();
    }

    public String getSpecialLabelText() {
        return settingPanelArea.findElement(By.xpath(".//label[@for='special']")).getText();
    }

    public String[] getWeaponClassCheckboxLabelTexts() {
        List<WebElement> lis = settingPanelArea.findElements(By.xpath(".//ui/li"));
        return lis.stream().map(e -> e.getText()).toArray(String[]::new);
    }

    public boolean isVisibleHowToUseModal() {
        return howToUseModal.isDisplayed();
    }

    public boolean isOpenedSettingPanel() {
        return Boolean.valueOf(settingPanelArea.findElement(By.xpath(".//h4/a")).getAttribute("aria-expanded"));
    }

    /* SCREEN TRANSITION */
    public IndexXhtmlPage openHowToUseModal() {
        howToUseLinkArea.findElement(By.xpath(".//button[@type='button']")).click();
        return new IndexXhtmlPage(driver);
    }

    public IndexXhtmlPage closeHowToUseModal() {
        howToUseModal.findElement(By.xpath(".//button[@type='button' and @class='btn btn-secondary']")).click();
        return new IndexXhtmlPage(driver);
    }

    public IndexXhtmlPage clickSettingPanel() {
        settingPanelArea.findElement(By.xpath(".//h4/a")).click();
        return new IndexXhtmlPage(driver);
    }

    public IndexXhtmlPage clickRandomizeButton() {
        randomizeBtnArea.findElement(By.xpath(".//input[@type='submit']")).click();
        return new IndexXhtmlPage(driver);
    }
}