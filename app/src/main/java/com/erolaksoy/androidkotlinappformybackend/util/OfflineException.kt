import java.io.IOException

//Exception -> IOException sınıfıyla değiştirildi.
class OfflineException(message: String) : IOException(message)