package cat.montilivi.webdriver

import org.junit.jupiter.api.Assertions.assertTrue
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

import kotlin.test.Test

class DuckDuckGoTest : TestBasic() {
    @FindBy(id = "searchbox_input")
    var searchBox: WebElement? = null

    @FindBy(className = "react-results--main")
    var resultsList: WebElement? = null

    @Test
    @Throws(Exception::class)
    fun testSearch() {
        PageFactory.initElements(driver, this)
        driver.get("https://duckduckgo.com/")
        Thread.sleep(2000)
        searchBox!!.sendKeys("pizza hawaiana")
        searchBox!!.submit()
        Thread.sleep(2000)
        assertTrue(resultsList!!.isDisplayed())
    }
}