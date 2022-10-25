import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtensionContainer

class Toolkit private constructor(private val extensions: ExtensionContainer) {
    companion object {
        private lateinit var instance: Toolkit
        fun get(extensions: ExtensionContainer): Toolkit {
            if (!::instance.isInitialized) {
                instance = Toolkit(extensions)
            }
            return instance
        }
    }

    operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): String =  get(property.name)

    operator fun get(key:String):String = extensions.extraProperties[key.replace("_", ".")]?.toString()!!
}
