package com.ihcl.payment.schema

import java.util.Date

data class Order(
    val orderId: String,
    val customerHash: String,
    val customerEmail: String,
    val customerId: String,
    val customerMobile: String,
    val channel: String,
    val currencyCode: String,
    val discountAmount: Double,
    val basePrice: Double?,
    val taxAmount: Double?,
    val gradTotal: Double,
    var payableAmount: Double,
    val isRefundable: Boolean,
    val orderType: OrderType,
    val transactionId: String?,
    val billingAddress: BillingAddress,
    val offers: List<Offers>,
    val orderLineItems: MutableList<OrderLineItem>,
    var modifyBookingCount: Int,
    var paymentDetails: TransactionInfo,
    var paymentMethod: String,
    var paymentStatus: String,
    var orderStatus: String,
    var transactionType: String?,
    val refundAmount: Double,
    var transactionStatus: String? = null,
    var retryCount: Int = 0,
    val agreedTnc: Boolean? = null,
    val agreedPrivacyPolicy: Boolean? = null,
    val createdTimestamp: Date? = null,
    var modifiedTimestamp: Date = Date(),
    var bookingCancelRemarks: String?,
    val brandName :String
)

data class TransactionInfo(
    var transaction_1: MutableList<PaymentDetail>?,
    var transaction_2: MutableList<PaymentDetail>?,
    var transaction_3: MutableList<PaymentDetail>?,
    var transaction_4: MutableList<PaymentDetail>?
)

data class OrderLineItem(
    val hotel: Hotel?,
    val giftCard: GiftCard?,
    val loyalty: Loyalty?
)

data class Hotel(
    val addOnDetails: List<AddOnDetail>,
    val address: Address,
    var bookingNumber: String?,
    val category: String,
    val hotelId: String?,
    val invoiceNumber: String,
    val invoiceUrl: String,
    val name: String?,
    val reservationId: String,
    val roomCount: Int,
    var adultCount: Int,
    var childrens: Int,
    var checkIn: String?,
    var checkOut: String?,
    val promoCode: String?,
    val promoType: String?,
    var rooms: List<Room>?,
    var status: String,
    val mobileNumber: String?,
    var country: String?,
    var storeId: String?,
    val hotelSponsorId:String?,
    var synxisId: String?,
    val emailId: String?,
    val specialRequest: String?,
    var totalDepositAmount: Double?,
    var balancePayable: Double?,
    var isDepositAmount: Boolean?,
    var isDepositPaid: Boolean?,
    var isDepositFull: Boolean? = false,
    val voucherRedemption: VoucherRedemptionAvailPrivileges?,
    val neupassMembershipId:String?,
    var revisedPrice: Double?,
    var grandTotal: Double?,
    var totalBasePrice: Double?,
    var totalTaxPrice: Double?,
    var amountPaid: Double?,
    var payableAmount: Double?,
    var refundAmount: Double?,
    var oldTotalBasePrice: Double?,
    var oldTotalTaxPrice: Double?,
    var oldGrandTotal: Double?,
    val totalPriceChange: Double?,
    val totalTaxChange: Double?,
    var totalCancelPayableAmount: Double? = 0.0,
    var totalCancelRefundableAmount: Double? = 0.0,
    var totalCancellationPenaltyAmount: Double? = 0.0,
    var totalCancellationPaidAmount: Double? = 0.0,
    var complementaryBasePrice: Double? = 0.0,
    var bookingNoOfNights: Int?,
    var totalCouponDiscountValue: Double,
    var isSeb: Boolean? = false,
    var sebRequestId: String? = null,
    val voucherNumber:String?,
    val voucherPin:String?
)

data class VoucherRedemptionAvailPrivileges(
    var bitDate: String?,
    var memberId: String?,
    var privileges: String?,
    var pin:String? = null,
    var availBitId: String? = null,
    var status: String? = null,
    var bitCategory: String? = null,
    val isComplementary: Boolean?,
    var type:String?
)

data class PaymentDetail(
    var paymentType: String?,
    var paymentMethod: String?,
    var paymentMethodType: String?,
    var txnGateway: Int?,
    var txnId: String?,
    var txnNetAmount: Double?,
    var txnStatus: String?,
    var txnUUID: String?,
    var cardNo: String?,
    var nameOnCard: String?,
    var userId: String?,
    var redemptionId: String?,
    var pointsRedemptionsSummaryId: String?,
    var externalId: String?,
    var cardNumber: String?,
    val cardPin: String?,
    var preAuthCode: String?,
    var batchNumber: String?,
    var approvalCode: String?,
    var transactionId: Int?,
    var transactionDateAndTime: String?,
    var expiryDate: String?,
    var ccAvenueTxnId: String?
)

data class Offers(
    val offerAmount: Double,
    val offerName: String,
    val offerType: String
)

data class Loyalty(
    val memberCardDetails: MemberCardDetails,
    val membershipDetails: MembershipDetails,
    val shareHolderDetails: ShareHolderDetails?,
    val memberShipPurchaseType: String? = null,
    val isBankUrl :Boolean =  false,
    val isShareHolder :Boolean =  false,
    val isTata :Boolean? =  false,
    val gravityVoucherCode: String?,
    val gravityVoucherPin: String?,
    val epicureFiestaOfferName: String?,
    val epicureFiestaOfferCode: String?
)

data class ShareHolderDetails(
    val membershipPlanName: String?,
    val membershipPlanCode: String?,
    val membershipPlanType: String?,
    val bankName: String?
)

data class MemberCardDetails(
    val enrolling_location: String,
    val enrolling_sponsor: Int,
    val enrollment_channel: String,
    val enrollment_touchpoint: Int,
    val extra_data: ExtraData,
    val epicure_price:Double,
    val taxAmount: Double,
    val discountPercent: Int? = null,
    val discountPrice: Double? = null,
    val discountTax: Double? = null,
    val addOnCardDetails: AddOnCardDetails?
)

data class ExtraData(
    val country_code: String,
    val domicile: String,
    val epicure_type: String,
    val state: String,
    val country: String,
    val gstNumber: String
)

data class MembershipDetails(
    val memberId: String,
    val mobile: String,
    val user: User,
    val addOnCardDetails: AddOnCardDetails?
)

data class AddOnCardDetails(
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val title: String?,
    val dateOfBirth:String?,
    val mobile:String?,
    val mobileCountryCode:String?,
    val obtainAddOnCard:Boolean
)

data class User(
    val email: String,
    val first_name: String,
    val last_name: String,
    val gender: String?,
    val salutation: String,
    val date_of_birth: String,
    val address: String,
    val pincode: String
)

enum class PaymentStatus {
    CHARGED
}

enum class OrderType {
    HOTEL_BOOKING, GIFT_CARD_PURCHASE, RELOAD_BALANCE, MEMBERSHIP_PURCHASE, HOLIDAYS
}
data class BillingAddress(
    val address1: String,
    val address2: String,
    val address3: String,
    val city: String,
    val country: String,
    val firstName: String,
    val lastName: String,
    val pinCode: String,
    val state: String,
    val phoneNumber: String,
    val countyCodeISO: String
)

data class GiftCard(
    val deliveryMethods: DeliveryMethodsDto,
    val quantity: Int,
    val isMySelf: Boolean?,
    val giftCardDetails: List<GiftCardDetailsDto>?,
    val promoCode: String?,
    val receiverAddress: ReceiverAddressDto?,
    val receiverDetails: ReceiverDetailsDto?,
    val senderDetails: SenderDetailsDto?
)

data class GiftCardDetailsDto(
    var amount: Double?,
    val sku: String?,
    val type: String?,
    val theme: String?,
    var cardNumber: String?,
    var cardPin: String?,
    var cardId: String?,
    var validity: String?,
    var orderId: String?
)

data class ReceiverAddressDto(
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val country: String,
    val pinCode: String,
    val state: String
)

data class ReceiverDetailsDto(
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val message: String?,
    val phone: String?,
    val rememberMe: Boolean?,
    val scheduleOn: String?
)

data class SenderDetailsDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val registerAsNeuPass: Boolean
)

data class DeliveryMethodsDto(
    val phone: String,
    val email: Boolean,
    val smsAndWhatsApp: Boolean
)

data class AddOnDetail(
    val addOnCode: String,
    val addOnDesc: String,
    val addOnName: String,
    val addOnPrice: Double,
    val addOnType: String
)

data class Address(
    val city: String,
    val contactNumber: String,
    val directions: String,
    val landmark: String,
    val lat: String,
    val long: String,
    val mapLink: String,
    val pinCode: String,
    val state: String,
    val street: String
)

data class Tax(
    var amount: Double?,
    val breakDown: List<BreakDown>?
)

data class BreakDown(
    val amount: Double?,
    val code: String?
)

data class Room(
    val isPackage: Boolean?,
    var confirmationId: String?,
    var cancellationId: String?,
    var status: String?,
    val addOnDetails: List<AddOnDetail>,
    val checkIn: String,
    val checkOut: String,
    val taxAmount: Double?,
    val tax: Tax?,
    val bookingPolicyDescription: String?,
    val daily: List<DailyRates>?,
    val cancelPolicyDescription: String?,
    val description: String?,
    val detailedDescription: String?,
    var penaltyAmount: Double?,
    var penaltyDeadLine: String?,
    var cancellationTime: String?,
    var penaltyApplicable: Boolean?,
    var cancelRemark: String?,
    val discountAmount: Double,
    val discountCode: String,
    var isModified: Boolean,
    val isRefundedItem: Boolean,
    val modifiedWith: String,
    val price: Double,
    val rateDescription: String,
    val refundedAmount: String,
    val roomDescription: String,
    val roomId: String,
    val roomName: String,
    val roomNumber: Int,
    val roomType: String,
    val rateCode: String?,
    val packageCode: String?,
    val adult: Int?,
    val children: Int?,
    val packageName: String?,
    val currency: String?,
    val travellerDetails: List<TravellerDetail>?,
    val roomImgUrl: String?,
    val changePrice: Double?,
    val changeTax: Double?,
    val modifyBooking: ModifyBooking?,
    val grandTotal: Double,
    var paidAmount: Double?,
    var roomCode: String?,
    var roomDepositAmount: Double?,
    var cancelPayableAmount: Double?,
    var cancelRefundableAmount: Double,
    var noOfNights: Int?,
    var couponDiscountValue: Double,
    var createdTimestamp: Date? = Date(),
    var modifiedTimestamp: Date? = Date()
)

data class DailyRates(
    var date:String?,
    var amount:Double?,
    var tax:TaxBreakDown?
)
data class TaxBreakDown(
    val amount: Double?,
    val breakDown: List<BreakDownDetails>?
)

data class BreakDownDetails(
    var amount: Double?,
    val code: String?,
    val name: String?
)

data class ModifyBooking(
    val isPackage: Boolean?,
    var confirmationId: String?,
    var cancellationId: String?,
    var status: String?,
    val addOnDetails: List<AddOnDetail>,
    val checkIn: String,
    val checkOut: String,
    val taxAmount: Double?,
    val tax: Tax?,
    val daily: List<DailyRates>?,
    val bookingPolicyDescription: String?,
    val cancelPolicyDescription: String?,
    var penaltyAmount: Double?,
    var penaltyDeadLine: String?,
    var cancellationTime: String?,
    var penaltyApplicable: Boolean?,
    val description: String?,
    val detailedDescription: String?,
    val discountAmount: Double,
    val discountCode: String,
    var isModified: Boolean,
    val isRefundedItem: Boolean,
    val modifiedWith: String,
    val price: Double,
    val rateDescription: String,
    val refundedAmount: String,
    val roomDescription: String,
    val roomId: String,
    val roomName: String,
    val roomNumber: Int,
    val roomType: String,
    val rateCode: String?,
    val packageCode: String?,
    val adult: Int?,
    val children: Int?,
    val packageName: String?,
    val currency: String?,
    val travellerDetails: List<TravellerDetail>?,
    val roomImgUrl: String?,
    var cancelRemark: String?,
    val grandTotal: Double,
    val paidAmount: Double?,
    var noOfNights: Int?,
    var roomCode: String?,
    var createdTimestamp: Date? = Date(),
    var modifiedTimestamp: Date? = Date()
)
data class TravellerDetail(
    val salutation:String?=null,
    val dateOfBirth: String,
    val address: String,
    val city: String,
    val countryCode: String,
    val customerId: String,
    val customerType: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val gstNumber: String,
    val lastName: String,
    val membershipNumber: String,
    val mobile: String,
    val name: String,
    val secondaryContact: String,
    val state: String
)
