package cat.montilivi.webdriver

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import kotlin.test.Test


class DisappearingElementsTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun testApp() {
        driver.navigate().to("https://the-internet.herokuapp.com/disappearing_elements")
        for (i in 0..9) {
            println("Iteración: " + (i + 1))
            val elementsDelMenu: WebElement =
                driver.findElement(
                    By.xpath(".//*[@id='content']/div/ul/li[last()]")
                )
            elementsDelMenu.click()
            driver.navigate().to("https://the-internet.herokuapp.com/disappearing_elements")
            driver.navigate().refresh()
        }
    }
}