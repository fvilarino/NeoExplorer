package com.francesc.neoexplorer.data.preferences.impl

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.francesc.neoexplorer.data.preferences.impl.proto.AppPreferencesProto
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/**
 * [Serializer] for [AppPreferencesProto] used by the DataStore instance.
 *
 * The default value has [AppPreferencesProto.theme] unset (proto3 default = 0 = AUTO) and
 * [AppPreferencesProto.useDynamicTheme] false, which is a safe first-launch state.
 */
internal object AppPreferencesSerializer : Serializer<AppPreferencesProto> {

    override val defaultValue: AppPreferencesProto = AppPreferencesProto.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppPreferencesProto =
        try {
            AppPreferencesProto.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot deserialize AppPreferencesProto", e)
        }

    override suspend fun writeTo(t: AppPreferencesProto, output: OutputStream) {
        t.writeTo(output)
    }
}
