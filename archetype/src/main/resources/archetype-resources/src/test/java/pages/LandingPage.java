#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.pages;

import io.magentys.cinnamon.webdriver.elements.PageElement;
import io.magentys.cinnamon.webdriver.support.FindByKey;
import org.openqa.selenium.support.FindBy;

public class LandingPage {

    // You can lookup locators using a key that is specified within an external locator.yml file
    @FindByKey("landing-page.main-menu")
    public PageElement mainMenuByKey;

    // You can also use the standard webdriver annotations for finding an element
    @FindBy(id = "mainMenuId") //TODO Replace with a valid id
    public PageElement mainMenu;
}