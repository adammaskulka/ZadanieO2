
package com.maskulka.zadanieo2.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> ViewGroup.viewBinding(): Lazy<T> {
    return lazy {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflateViewBinding(T::class.java, layoutInflater, this, true)
    }
}

inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding(): Lazy<T> {
    return lazy {
        inflateViewBinding(T::class.java, layoutInflater, null, false)
    }
}

inline fun <reified T : ViewBinding> Fragment.viewBinding(): ReadOnlyProperty<Fragment, T> {
    return FragmentViewBindingDelegate(T::class.java)
}

@PublishedApi
internal fun <T : ViewBinding> inflateViewBinding(clazz: Class<T>, layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean): T {
    // Find the appropriate inflate method for clazz
    // Method signature may be different depending on the type of the object that will use ViewBinding
    val inflateMethod = tryOrNull { clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java) }
        .orElse { tryOrNull { clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java) } }
        .orElse { tryOrNull { clazz.getMethod("inflate", LayoutInflater::class.java) } }
        .orElse { throw NoSuchMethodException("Could not find an inflate method for ${clazz.name}") }
    val parameters = listOf(layoutInflater, parent, attachToParent).take(inflateMethod.parameterTypes.count())

    @Suppress("UNCHECKED_CAST")
    return inflateMethod.invoke(null, *parameters.toTypedArray()) as T
}

private fun <T : ViewBinding> bindViewBinding(clazz: Class<T>, view: View): T {
    val bindMethod = tryOrNull { clazz.getMethod("bind", View::class.java) }
        .orElse { throw NoSuchMethodException("Could not find a bind method for ${clazz.name}") }

    @Suppress("UNCHECKED_CAST")
    return bindMethod.invoke(null, view) as T
}

@PublishedApi
internal class FragmentViewBindingDelegate<T : ViewBinding>(private val clazz: Class<T>) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    private var viewLifecycle: Lifecycle? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val viewLifecycle = thisRef.viewLifecycleOwner.lifecycle
        if (this.viewLifecycle != viewLifecycle) {
            this.viewLifecycle = viewLifecycle

            viewLifecycle.addObserver(object : DefaultLifecycleObserver {

                override fun onDestroy(owner: LifecycleOwner) {
                    // Set binding to null when fragment view is destroyed as described here:
                    // https://developer.android.com/topic/libraries/view-binding#fragments
                    binding = null
                }
            })
        }

        return binding.orElse {
            bindViewBinding(clazz, thisRef.requireView()).also {
                binding = it
            }
        }
    }
}
