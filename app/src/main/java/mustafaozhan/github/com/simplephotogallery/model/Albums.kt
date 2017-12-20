package mustafaozhan.github.com.simplephotogallery.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Mustafa Ozhan on 12/20/17 at 5:48 PM on Arch Linux.
 */
data class Albums(private var folderNames: String,
                  private var imagePath: String,
                  private var imageCount: Int,
                  private var isVideo: Boolean) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte())

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(folderNames)
        dest?.writeString(imagePath)
        dest?.writeInt(imageCount)
        dest?.writeByte(if (isVideo) 1 else 0)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Albums> {
        override fun createFromParcel(parcel: Parcel): Albums {
            return Albums(parcel)
        }

        override fun newArray(size: Int): Array<Albums?> {
            return arrayOfNulls(size)
        }
    }
}