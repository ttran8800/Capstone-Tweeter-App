# echo "Phase 1: Starting server-registry service..."
# find services -name 'server-registry-service.yaml' -exec echo "Found file: {}" \; -exec kubectl apply -f {} \;

# echo "Phase 2: Starting server-registry deployment..."
# find deployments -name 'server-registry-deployment.yaml' -exec echo "Found file: {}" \; -exec kubectl apply -f {} \;

# echo "Phase 3: Starting api-gateway service..."
# find services -name 'api-gateway-service.yaml' -exec echo "Found file: {}" \; -exec kubectl apply -f {} \;

# echo "Phase 4: Starting api-gateway deployment..."
# find deployments -name 'api-gateway-deployment.yaml' -exec echo "Found file: {}" \; -exec kubectl apply -f {} \;

echo "Phase 1: Starting persistent storage..."
for file in ./volumes/*.yaml; do
    echo "Applying $file..."
    kubectl apply -f "$file"
done

echo "Phase 2: Starting all kube services..."
for file in ./services/*.yaml; do 
    echo "Applying $file..." 
    kubectl apply -f "$file" 
done

echo "Phase 3: Starting all kube deployments..."
for file in ./deployments/*.yaml; do 
    echo "Applying $file..." 
    kubectl apply -f "$file" 
done