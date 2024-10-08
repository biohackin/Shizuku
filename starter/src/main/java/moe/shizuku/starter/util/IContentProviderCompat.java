package moe.shizuku.starter.util;

import android.content.AttributionSource;
import android.content.IContentProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.system.Os;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class IContentProviderCompat {

    @Nullable
    public static Bundle call(@NonNull IContentProvider provider, @Nullable String attributeTag, @Nullable String callingPkg, @Nullable String authority, @Nullable String method, @Nullable String arg, @Nullable Bundle extras) throws RemoteException {
        Bundle reply = null;
        if (Build.VERSION.SDK_INT >= 31) {
            try {
                reply = provider.call((new AttributionSource.Builder(Os.getuid())).setAttributionTag(attributeTag).setPackageName(callingPkg).build(), authority, method, arg, extras);
            } catch (LinkageError e) {
                e.printStackTrace();
            }
        } else if (Build.VERSION.SDK_INT >= 30) { }
        else if (Build.VERSION.SDK_INT >= 29) { }
        else {
            reply = provider.call(callingPkg, method, arg, extras);
        }

        return reply;
    }
}
