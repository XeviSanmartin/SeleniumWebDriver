package cat.montilivi.webdriver

import kotlin.test.Test


class InfiniteScrollTest : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun infiniteScrollTest() {
        driver.navigate().to("http://the-internet.herokuapp.com/infinite_scroll")
        for (i in 0..4) {
            println("Iteración: " + (i + 1))
            jse.executeScript("window.scrollTo(0, document.body.scrollHeight);")
            Thread.sleep(2500)
        }
    }
}