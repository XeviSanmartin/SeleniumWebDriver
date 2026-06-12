package cat.montilivi.webdriver

import kotlin.test.Test

class InterrogationTest : TestBasic() {
    @Test //Opciones de Navegación
    @Throws(Exception::class)
    fun testNavigation() {
        driver.get("https://www.duckduckgo.com/") //Navegar hasta duckduckgo
        println("---------------------")
        System.out.println(driver.getPageSource())
        println("---------------------")
        System.out.println(driver.getTitle())
        println("---------------------")
        System.out.println(driver.getCurrentUrl())
        println("---------------------")
    }
}