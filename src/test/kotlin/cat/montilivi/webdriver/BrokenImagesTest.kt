package cat.montilivi.webdriver

import junit.framework.Assert
import net.lightbody.bmp.BrowserMobProxy
import net.lightbody.bmp.BrowserMobProxyServer
import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.core.har.Har
import net.lightbody.bmp.core.har.HarEntry
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.Proxy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.junit.jupiter.api.Assertions.assertFalse



import java.io.File
import kotlin.test.Test

class BrokenImages {
    var driver: WebDriver? = null
    var proxy: BrowserMobProxy? = null

    @BeforeEach
    @Throws(Exception::class)
    fun setUp() {
        // 1. Iniciar el servidor Proxy localmente
        proxy = BrowserMobProxyServer()
        proxy!!.start(0) // El puerto 0 le dice que busque cualquier puerto libre

        // 2. Crear el objeto Proxy de Selenium a partir del BrowserMob
        val seleniumProxy: Proxy? = ClientUtil.createSeleniumProxy(proxy)

        // 3. Inyectar el proxy en las opciones de Firefox
        val options = FirefoxOptions()
        options.setProxy(seleniumProxy)


        // Nota: Si usas Selenium 4.6+, esta línea ya no es necesaria gracias a Selenium Manager
        System.setProperty(
            "webdriver.gecko.driver",
            ("src" + File.separator + "test"
                    + File.separator + "resources"
                    + File.separator + "geckodriver-macos")
        )

        driver = FirefoxDriver(options)
    }

    @AfterEach
    fun tearDown() {
        // Es crucial apagar ambos servicios para no dejar procesos huérfanos
        if (driver != null) {
            driver!!.quit()
        }
        if (proxy != null) {
            proxy!!.stop()
        }
    }

    @Test
    fun allImagesLoaded() {
        // 4. Iniciar la captura de un nuevo archivo HAR
        proxy!!.newHar("captura_imagenes")

        // 5. Navegar a la página (el proxy registrará todo el tráfico)
        driver!!.navigate().to("http://the-internet.herokuapp.com/broken_images")

        // 6. Extraer el HAR generado
        val har: Har = proxy!!.getHar()

        // 7. Analizar las entradas de red
        var hasBrokenImages = false
        val entries: MutableList<HarEntry> = har.getLog().getEntries()

        for (entry in entries) {
            val url: String = entry.getRequest().getUrl()
            val statusCode: Int = entry.getResponse().getStatus()


            // Filtramos para evaluar solo los recursos que son imágenes
            if (url.endsWith(".jpg") || url.endsWith(".png") || url.endsWith(".gif")) {
                if (statusCode == 404) {
                    System.err.println("❌ Imagen rota detectada (404): " + url)
                    hasBrokenImages = true
                } else {
                    println("✅ Imagen cargada correctamente (" + statusCode + "): " + url)
                }
            }
        }
        // 8. El test fallará si al menos una imagen devolvió 404
        Assert.assertFalse("Se encontraron imágenes con error 404 en la página.", hasBrokenImages)
    }
}