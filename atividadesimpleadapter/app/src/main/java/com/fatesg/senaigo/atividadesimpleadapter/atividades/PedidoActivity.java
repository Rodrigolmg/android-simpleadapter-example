package com.fatesg.senaigo.atividadesimpleadapter.atividades;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fatesg.senaigo.atividadesimpleadapter.R;
import com.fatesg.senaigo.atividadesimpleadapter.classes.Pedido;
import com.fatesg.senaigo.atividadesimpleadapter.classes.Produto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class PedidoActivity extends AppCompatActivity {


    protected AlertDialog alerta;
    protected EditText txtNomeCliente;
    protected Spinner spnProdutos;
    protected ListView lstProdutos;

    protected Produto produto;
    protected Pedido pedido = new Pedido();

    protected List<HashMap<String, String>> produtos = new ArrayList<>();
    protected ArrayList<Pedido> pedidos = new ArrayList<>();
    protected ArrayList<HashMap<String,String>> listaExclusao = new ArrayList<>();

    protected Long id = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        capturarCampos();
        adicionarProdutosSpinner();
    }

    public void adicionarPedidoLista(View view) {
        lstProdutos.setVisibility(View.VISIBLE);
        HashMap<String, String> prods = new HashMap<>();

        produto = new Produto();
        produto = (Produto)spnProdutos.getSelectedItem();

        if(produto.getQuantidadeProduto() == null){
            produto.setQuantidadeProduto(1);
        } else {
            produto.setQuantidadeProduto(produto.getQuantidadeProduto() + 1);
        }

        prods.put("idProduto", String.valueOf(produto.getId()));
        prods.put("nomeProduto", String.valueOf(produto.getNomeProduto()));
        prods.put("valorProduto", String.valueOf(produto.getValorProduto()));
        prods.put("quantidadeProduto", String.valueOf(produto.getQuantidadeProduto()));

        if(!produtos.isEmpty()) {
            for (HashMap<String, String> hashMap : produtos) {
                if (Objects.equals(hashMap.get("idProduto"), prods.get("idProduto"))) {
                    produtos.remove(hashMap);
                }
            }
        }

        produtos.add(prods);
        pedido.setProdutos(produtos);

        String[] from = {"idProduto", "nomeProduto", "valorProduto", "quantidadeProduto"};
        int[] to = {R.id.idProduto, R.id.nomeProduto, R.id.valorProduto, R.id.quantidadeProduto};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this,
                pedido.getProdutos(), R.layout.item_listview_pedido, from, to);

        lstProdutos.setAdapter(simpleAdapter);
    }


    public void realizarPedido(View view) {

        if(txtNomeCliente.getText().toString().isEmpty()){
            Toast.makeText(this, "Digite seu nome!", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<Produto> listaProdutos = new ArrayList<>();

        if(produtos.isEmpty()){
            Toast.makeText(this, "Adicione pelo menos um produto na lista!", Toast.LENGTH_LONG).show();
            return;
        }

        for(HashMap<String,String> hashMap: produtos){
            produto = new Produto();
            produto.setId(Long.parseLong(Objects.requireNonNull(hashMap.get("idProduto"))));
            produto.setNomeProduto(hashMap.get("nomeProduto"));
            produto.setValorProduto(BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(hashMap.get("valorProduto")))));
            produto.setQuantidadeProduto(Integer.parseInt(Objects.requireNonNull(hashMap.get("quantidadeProduto"))));
            listaProdutos.add(produto);
        }

        BigDecimal valorTotal = BigDecimal.ZERO;

        for(Produto produto: listaProdutos){
             valorTotal = valorTotal.add(produto.getValorProduto().multiply(BigDecimal.valueOf(produto.getQuantidadeProduto())));
        }

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        pedido.setId(id+1);
        pedido.setNomeCliente(txtNomeCliente.getText().toString());
        pedido.setDataDoPedido(df.format(new Date()));
        pedido.setValorTotal(valorTotal);

        alertar(pedido);

        pedidos.add(pedido);
        id++;
//        Toast.makeText(this, String.valueOf(valorTotal), Toast.LENGTH_LONG).show();
    }

    public void limparCampos(View view) {
        limpar();
    }

    private void adicionarProdutosSpinner(){
        ArrayList<Produto> spnProdutos = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00");

        for(Long i = 0L; i < 10; i++){
            StringBuilder strProd = new StringBuilder();

            produto = new Produto();

            strProd.append("Produto ").append(String.valueOf(i+1));

            produto.setNomeProduto(strProd.toString());
            produto.setId(i+1);

            double multiplicador = i % 2 == 0 ? 10 : 100;
            double valorProduto =
                    Double.parseDouble(
                            df.format(new Random().nextDouble() * multiplicador)
                                    .replace(',', '.'));

            produto.setValorProduto(BigDecimal.valueOf(valorProduto));
            spnProdutos.add(produto);
        }

        ArrayAdapter<Produto> produtoArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spnProdutos);

        produtoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        this.spnProdutos.setAdapter(produtoArrayAdapter);
    }

    private void capturarCampos(){
        txtNomeCliente = findViewById(R.id.txtNomeCliente);
        spnProdutos = findViewById(R.id.spnProdutos);
        spnProdutos = findViewById(R.id.spnProdutos);
        lstProdutos = findViewById(R.id.lstProdutos);
    }

    private void alertar(Pedido pedido){

        StringBuilder msg = new StringBuilder();

        AlertDialog. Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmação do pedido");

        msg.append("Código do pedido: ").append(String.valueOf(pedido.getId()));
        msg.append("\nCliente: ").append(pedido.getNomeCliente());
        msg.append("\nData do pedido: ").append(pedido.getDataDoPedido());
        msg.append("\n\nProdutos:\n\n");

        for(HashMap<String, String> hashMap: pedido.getProdutos()){
            produto = new Produto();
            produto.setNomeProduto(hashMap.get("nomeProduto"));
            produto.setValorProduto(BigDecimal.valueOf(Double.parseDouble(Objects.requireNonNull(hashMap.get("valorProduto")))));
            produto.setQuantidadeProduto(Integer.parseInt(Objects.requireNonNull(hashMap.get("quantidadeProduto"))));
            msg.append(produto.getNomeProduto()).append(" - R$")
                    .append(String.valueOf(produto.getValorProduto())).append(" - ")
                    .append(produto.getQuantidadeProduto()).append(" unidades;\n");
        }

        msg.append("\nValor total: R$").append(pedido.getValorTotal());

        builder.setMessage(msg.toString());

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alerta.cancel();
            }
        });

        alerta = builder.create();
        alerta.show();
        this.limpar();
    }

    private void limpar(){
        lstProdutos.setVisibility(View.GONE);
        produtos.clear();
        lstProdutos.setAdapter(null);
        txtNomeCliente.setText("");

        for(int i = 0; i < spnProdutos.getCount(); i++){
            produto = new Produto();
            spnProdutos.setSelection(i);
            produto = (Produto)spnProdutos.getSelectedItem();
            produto.setQuantidadeProduto(0);
        }

        spnProdutos.setSelection(0);
    }
}
