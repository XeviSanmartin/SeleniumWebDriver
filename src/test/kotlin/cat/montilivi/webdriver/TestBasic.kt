package cat.montilivi.webdriver

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.safari.SafariDriver
import java.time.Duration
import kotlin.test.assertEquals

open class TestBasic {
    lateinit var driver: WebDriver

    @BeforeEach
    fun setupTest() {
        // Llegim la propietat injectada per Gradle/terminal, o usem Chrome per defecte
        val navegador = System.getProperty("Navegador")?.lowercase() ?: "chrome"

        // Instanciem directament. Zero configuracions de rutes o mànagers externs.
        driver = when (navegador) {
            "firefox" -> FirefoxDriver()
            "edge" -> EdgeDriver()
            "safari" -> SafariDriver()
            else -> {
                val options = ChromeOptions()
                // options.addArguments("--headless") // Per executar sense obrir la finestra visual
                ChromeDriver(options)
            }
        }

        // Configurem l'espera implícita per donar temps a carregar els elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30))
    }

//    @Test
//    fun `comprova que la cerca de google carrega el titol correcte`() {
//        // Acció: Navegar a una URL
//        driver.get("https://www.google.com")
//
//        // Asserciò: Comprovar que el títol de la pestanya és correcte
//        assertEquals("Google", driver.title)
//    }

    @AfterEach
    fun teardown() {
        // Tanca el navegador i destrueix la sessió
        //driver.quit()
    }
}