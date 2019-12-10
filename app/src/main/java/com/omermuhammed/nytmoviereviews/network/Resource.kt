package com.omermuhammed.nytmoviereviews.network

/**
 * Represents a network bound resource. Each subclass represents the resource's state:
 * - [Loading]: the resource is being retrieved from network.
 * - [Success]: the resource has been retrieved (available in [Success.data] field)
 * - [Failure]: the resource retrieving has failed
 *
 * A commonly used mechanism, sometimes called "Result" class
 */
sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val errorMessage: String) : Resource<T>()
}