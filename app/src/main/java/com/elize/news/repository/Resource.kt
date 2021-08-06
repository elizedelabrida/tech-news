package com.elize.news.repository

class Resource<T>(
    val data: T?,
    val error: String? = null
)

fun <T> createFailResource(
    currentResource: Resource<T>?,
    error: String?
): Resource<T> {
    if (currentResource != null) {
        return Resource(data = currentResource.data, error = error)
    }
    return Resource(data = null, error = error)
}

fun createEmptyResource(): Resource<Void> =
    Resource(null)