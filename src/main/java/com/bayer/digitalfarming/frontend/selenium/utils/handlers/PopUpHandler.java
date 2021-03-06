package com.bayer.digitalfarming.frontend.selenium.utils.handlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.bayer.digitalfarming.frontend.selenium.utils.config.PropertiesRepository;

public class PopUpHandler extends BaseHandler {
	private Logger logger = LogManager.getLogger(this.getClass());

	public PopUpHandler(WebDriver driver) {
		super(driver);
	}

	public boolean isAlertPresent() {
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(driver, PropertiesRepository.getInt("global.driver.wait"));
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (TimeoutException eTO) {
			foundAlert = false;
		}
		return foundAlert;
	}

	public Alert switchToAlert(String... waitFor) {
		Alert alert = null;
		try {
			driver.switchTo().defaultContent();
			alert = null;
			if (isAlertPresent()) {
				alert = driver.switchTo().alert();
				logger.info("Switching to Alert........");
			}
		} catch (WebDriverException e) {
			logger.error("Unable to switch to the alert.\n" + e.getMessage());
			throw new WebDriverException("Unable to switch to the alert.\n" + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
		return alert;

	}

	public void acceptAlert(String... waitFor) {
		try {
			if (isAlertPresent()) {
				Alert alert = driver.switchTo().alert();
				alert.accept();
			}
		} catch (WebDriverException e) {
			logger.error("Unable accept the alert \n " + e.getMessage());
			throw new WebDriverException("Unable accept the alert \n " + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public void dismissAlert(String... waitFor) {
		try {
			driver.switchTo().defaultContent();
			if (isAlertPresent()) {
				Alert alert = driver.switchTo().alert();
				alert.dismiss();
			}
		} catch (WebDriverException e) {
			logger.error("Unable dismiss the alert \n " + e.getMessage());
			throw new WebDriverException("Unable dismiss the alert \n " + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}

	public String getAlertText(String... waitFor) {
		String alertText = null;
		try {
			/* driver.switchTo().defaultContent(); */
			if (isAlertPresent()) {
				Alert alert = driver.switchTo().alert();
				logger.info("Switching to alert");
				alertText = alert.getText();
			}

		} catch (WebDriverException e) {
			logger.error("Unable get the text from an alert \n " + e.getMessage());
			throw new WebDriverException("Unable get the text from an alert \n " + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
		return alertText;
	}

	public void loginWithoutPopup(String urlWithoutHTTP, String userName, String password, String... waitFor) {
		try {
			String newURL = "http://" + userName + ":" + password + "@" + urlWithoutHTTP;
			driver.get(newURL);
		} catch (WebDriverException e) {
			logger.error("Unable top login to the application with windows authentication.\n" + e.getMessage());
			throw new WebDriverException("Unable top login to the application with windows authentication.\n" + e);
		}
		if (waitFor != null && waitFor.length > 0) {
			setWebDriverWait(waitFor[0]);
		}
	}
}
