package cat.montilivi.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.junit.jupiter.api.Test

class NestedFramesTest : TestBasic() {
    fun listIframesFromPage(driver: WebDriver) {
        val iframes = driver.findElements(By.tagName("frame"))
        for (iframe in iframes) {
            println(iframe.getAttribute("id"))
            println(iframe.getAttribute("name"))
        }
    }

    @Test
    fun testApp() {
        driver.navigate().to("https://the-internet.herokuapp.com/nested_frames")
        listIframesFromPage(driver)
        driver.switchTo().frame("frame-bottom")
        System.out.println(driver.findElement(By.xpath("html/body")).getText())
        driver.switchTo().defaultContent()
        driver.switchTo().frame("frame-top")
        listIframesFromPage(driver)
        driver.switchTo().frame("frame-middle")
        System.out.println(driver.findElement(By.xpath("html/body")).getText())
    }
}