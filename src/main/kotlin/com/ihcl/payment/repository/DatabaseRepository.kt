package com.ihcl.payment.repository

import com.ihcl.payment.config.MongoConfig
import com.ihcl.payment.exception.HttpResponseException

import com.ihcl.payment.exception.InternalServerException
import com.ihcl.payment.schema.Order
import com.mongodb.BasicDBObject
import com.mongodb.client.model.FindOneAndUpdateOptions
import io.ktor.http.*
import org.litote.kmongo.eq
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class DatabaseRepository {
    val log: Logger = LoggerFactory.getLogger(javaClass)
    private val conn = MongoConfig.getDatabase()
        .getCollection<Order>()

    suspend fun getOrder(orderId: String?): Order {
        return conn.findOne(Order::orderId eq orderId)
            ?: throw HttpResponseException("order $orderId Not found",HttpStatusCode.OK)
    }
    suspend fun findOneAndUpdateOrder(orderId: String,order: Order) {
        val updateObject = BasicDBObject()
        updateObject["\$set"] = order
        conn.findOneAndUpdate(order::orderId eq orderId, updateObject, FindOneAndUpdateOptions().upsert(true))
    }

    suspend fun findOrderByOrderIdAndCustomerHash(orderId: String, customerHash: String): Order{
        val order = conn.findOne(Order::orderId eq orderId, Order::customerHash eq customerHash)
        return order ?: throw HttpResponseException("Order not found!!", HttpStatusCode.BadRequest)
    }
}