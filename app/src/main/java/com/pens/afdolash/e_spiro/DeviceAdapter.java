package com.pens.afdolash.e_spiro;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by afdol on 5/17/2018.
 */

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.MyViewHolder> {

    public static final String EXTRA_DEVICE_ADDRESS = "address";

    private Context context;
    private List<BluetoothDevice> devices;

    public DeviceAdapter(Context context, List<BluetoothDevice> devices) {
        this.context = context;
        this.devices = devices;
    }

    @Override
    public DeviceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DeviceAdapter.MyViewHolder holder, int position) {
        final BluetoothDevice device = devices.get(position);

        holder.tvName.setText(device.getName());
        holder.tvAddress.setText(device.getAddress());
        holder.cardDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    try {
                        Intent intent = new Intent(context, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(EXTRA_DEVICE_ADDRESS, device.getAddress());
                        context.startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(context, "Bluetooth connection failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    device.createBond();
                    Toast.makeText(context, "Create bond!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAddress;
        public CardView cardDevice;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            cardDevice = (CardView) itemView.findViewById(R.id.card_device);
        }
    }
}
