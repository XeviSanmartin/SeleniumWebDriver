package cat.montilivi.webdriver

import kotlin.test.Test


class BasicAuth : TestBasic() {
    @Test
    @Throws(InterruptedException::class)
    fun testApp() {
        driver.navigate().to("https://admin:admin@the-internet.herokuapp.com/basic_auth")
        Thread.sleep(5000)
    }
}
