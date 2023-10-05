package io.pfuenzle.rerootcloak;

import android.content.Context;
import android.widget.Toast;

import com.topjohnwu.superuser.Shell;

import java.util.List;

public class RootUtil {
    static {
        // Set settings before the main shell can be created
        Shell.enableVerboseLogging = BuildConfig.DEBUG;
        Shell.setDefaultBuilder(Shell.Builder.create()
                .setFlags(Shell.FLAG_REDIRECT_STDERR)
                .setTimeout(10)
        );
    }
    public RootUtil() {
        //Run new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(!haveRootShell()) {
                    Shell.getShell();
                }
            }
        }).start();
    }

    public boolean haveRootShell(){
        return Boolean.TRUE.equals(Shell.isAppGrantedRoot());
    }

    public void runCommand(String command, Context context) {
        if(!haveRootShell()) {
            Toast.makeText(context, "Root access not granted", Toast.LENGTH_SHORT).show();
            return;
        }

        Shell.Result result;
        result = Shell.cmd(command).exec();
        if(result.getCode() != 0) {
            Toast.makeText(context, "Failed to run command", Toast.LENGTH_SHORT).show();
        }
    }
}
